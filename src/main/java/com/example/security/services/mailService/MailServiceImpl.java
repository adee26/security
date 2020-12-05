package com.example.security.services.mailService;

import com.example.security.entities.User;
import com.example.security.model.MailModel;
import com.example.security.services.mailService.MailService;
import com.example.security.utils.EmailTemplate;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender emailSender;
    private final Configuration emailConfiguration;

    @Value("${email.from}")
    private String from;

    public MailServiceImpl(JavaMailSender emailSender, @Qualifier(value = "emailConfigBean") Configuration emailConfiguration) {
        this.emailSender = emailSender;
        this.emailConfiguration = emailConfiguration;
    }

    @Override
    public void sendEmail(MailModel mailModel, User user) throws MessagingException, IOException, TemplateException {
        Map model = new HashMap();
        model.put("name", user.getUsername());

        mailModel.setContent(model);

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Template template = emailConfiguration.getTemplate("email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getContent());
        mimeMessageHelper.setTo(user.getUserPersonalInfo().getEmail());
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setFrom("apetreiadelina@gmail.com");
        emailSender.send(mimeMessage);
    }


    public void sendChristmasEmail(MailModel mailModel, User user) throws MessagingException, IOException, TemplateException {
        Map model = new HashMap();
        model.put("name", user.getUsername());

        mailModel.setContent(model);

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Template template = emailConfiguration.getTemplate("christmas.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getContent());
        mimeMessageHelper.setTo(user.getUserPersonalInfo().getEmail());
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setFrom(mailModel.getFrom());
        mimeMessageHelper.setSubject("Account created.");


        emailSender.send(mimeMessage);
    }

    @Override
    public void send(MailModel mailModel) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Template template = emailConfiguration.getTemplate(EmailTemplate.REGISTER);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getContent());
        mimeMessageHelper.setTo(mailModel.getTo());
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setFrom(this.from);
        mimeMessageHelper.setSubject(mailModel.getSubject());
        if(mailModel.getCc() != null){
            mimeMessageHelper.setCc(mailModel.getCc());
        }
        if(mailModel.getBcc() != null){
            mimeMessageHelper.setBcc(mailModel.getBcc());
        }
        emailSender.send(mimeMessage);
    }


}
