package com.example.security.controllers;

import com.example.security.entities.User;
import com.example.security.services.UserService;
import org.springframework.web.bind.annotation.*;

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
}
