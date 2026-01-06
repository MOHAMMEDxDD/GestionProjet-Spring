package com.planification.gestionprojetweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planification.gestionprojetweb.model.User;
import com.planification.gestionprojetweb.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}