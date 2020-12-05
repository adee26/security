package com.example.security.services.mailService;

import com.example.security.entities.User;
import com.example.security.model.MailModel;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailService {
    void sendEmail(MailModel mailModel, User user) throws MessagingException, IOException, TemplateException;
    void sendChristmasEmail(MailModel mailModel, User user) throws MessagingException, IOException, TemplateException;
    void send(MailModel mailModel) throws MessagingException, IOException, TemplateException;

}
