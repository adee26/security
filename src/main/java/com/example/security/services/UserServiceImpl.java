package com.example.security.services;

import com.example.security.entities.User;
import com.example.security.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User createUser(User user) {
        String password = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User newUser = new User(user);
           // User newUser = new User(id, user.getUsername(), user.getPassword(), user.getEnabled(), user.getUserAuthorities());
            userRepository.save(newUser);
            return newUser;
        }
        return null;
    }


}
