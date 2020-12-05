package com.example.security.services;

import com.example.security.dto.UserDTO;
import com.example.security.entities.User;
import com.example.security.model.MailModel;
import com.example.security.repositories.UserPersonalInfoRepository;
import com.example.security.repositories.UserRepository;
import com.example.security.services.mailService.MailService;
import com.example.security.utils.DTOMapper;
import com.example.security.utils.EmailTemplate;
import freemarker.template.TemplateException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserPersonalInfoRepository userPersonalInfoRepository;
    private final MailService mailService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserPersonalInfoRepository userPersonalInfoRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userPersonalInfoRepository = userPersonalInfoRepository;
        this.mailService = mailService;
    }

    private MailModel createMailModelForCreateUser(User user){
        MailModel mailModel = new MailModel();
        mailModel.setTo(user.getUserPersonalInfo().getEmail());
        HashMap <String, String> content = new HashMap<>();
        content.put("name", user.getUsername());
        mailModel.setContent(content);
        mailModel.setTemplate(EmailTemplate.REGISTER);
        mailModel.setName(user.getUsername());
        mailModel.setSubject("Activation email");
        return mailModel;
    }

    @Override
    public User createUser(User user) throws MessagingException, IOException, TemplateException {
        String password = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        User savedUser = userRepository.save(user);
        MailModel mailModel = createMailModelForCreateUser(user);
        mailService.send(mailModel);
        return savedUser;
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) {
        User optionalUser = userRepository.findUserById(id);
        optionalUser.setPassword(passwordEncoder.encode(user.getPassword()));
        optionalUser.setEnabled(user.getEnabled());
        optionalUser.setUserAuthorities(user.getUserAuthorities());
        optionalUser.setUserPersonalInfo(user.getUserPersonalInfo());

        userRepository.save(optionalUser);
        return optionalUser;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    public boolean isAdmin(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        for(GrantedAuthority userAuthority : userDetails.getAuthorities()){
            if(userAuthority.toString().equals("ADMIN")){
                return true;
            }
        }

        return false;
    }

    @Override
    public User findByIdAdmin(Long id, Authentication authentication) throws Exception {
        boolean isAdmin = this.isAdmin(authentication);
        if(isAdmin) {
            return userRepository.findUserById(id);
        }else{
            throw new Exception("User is not admin!");
        }
    }


    @Override
    public String deleteById(Long id){
        userRepository.deleteById(id);
        return "User was deleted";
    }

    @Override
    public UserDTO findByIdUser(Long id){
        User user = userRepository.findUserById(id);
        return DTOMapper.mapUserToDTO(user);
    }

}
