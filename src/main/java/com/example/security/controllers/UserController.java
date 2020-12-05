package com.example.security.controllers;

import com.example.security.dto.UserDTO;
import com.example.security.entities.User;
import com.example.security.model.MailModel;
import com.example.security.repositories.UserRepository;
import com.example.security.services.UserService;
import freemarker.template.TemplateException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @PostMapping("/user")
    public User saveUser( @RequestBody User user) throws MessagingException, IOException, TemplateException {
        return userService.createUser(user);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @GetMapping("/admin/users")
    public List<User> findAll(){
        return userService.findAllUsers();
    }

    @GetMapping("/admin/user/{id}")
    public User findById(@PathVariable Long id, Authentication authentication) throws Exception {
        return userService.findByIdAdmin(id, authentication);
    }

    @DeleteMapping("/user/{id}")
    public String deleteById(@PathVariable("id") Long id){
       return userService.deleteById(id);
    }

    @GetMapping("/user/{id}")
    public UserDTO findByIdUser(@PathVariable Long id){
        return userService.findByIdUser(id);
    }

    @GetMapping("activation/{uuid}")
    public String activateUser(@PathVariable String uuid){
       Optional<User> user = userRepository.findUserByuuid(uuid);
       if(user.isPresent()){
           User user1 = user.get();
           if(user1.getEnabled() == false){
               user1.setEnabled(true);
               user1.setUuid(null);
               userRepository.save(user1);
               return "Your account was activated!";
           }else{
                return "This username has already been activated!";
           }
       }else{
          return "Invalid activation code.";
       }

    }


}
