package com.example.security.services;

import com.example.security.entities.User;

public interface UserService {
    User createUser(User user);
    User updateUser(Long id, User user);
}
