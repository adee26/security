package com.example.security.controllers;

import com.example.security.dto.UserDTO;
import com.example.security.entities.User;
import com.example.security.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public User saveUser(@RequestBody User user){
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

}
