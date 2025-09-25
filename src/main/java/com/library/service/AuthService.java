package com.library.service;


import com.library.model.User;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public User authenticate(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void createDefaultUsers() {
        if (userRepository.findByUsername("admin") == null) {
            userRepository.save(new User("admin", "admin123", User.Role.ADMIN));
        }
        if (userRepository.findByUsername("librarian") == null) {
            userRepository.save(new User("librarian", "lib123", User.Role.LIBRARIAN));
        }
    }
}
