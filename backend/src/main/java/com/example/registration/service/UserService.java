package com.example.registration.service;

import com.example.registration.entity.User;
import com.example.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        User user = userOptional.get();
        
        // Check if account is locked
        if (user.isLocked()) {
            throw new RuntimeException("Account is locked. Try again later.");
        }
        
        // Verify password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            user.incrementLoginAttempts();
            userRepository.save(user);
            throw new RuntimeException("Invalid password");
        }
        
        // Reset login attempts on successful login
        user.resetLoginAttempts();
        return userRepository.save(user);
    }
}