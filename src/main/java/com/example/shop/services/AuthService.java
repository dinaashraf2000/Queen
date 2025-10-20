package com.example.shop.services;

import com.example.shop.entities.User;
import com.example.shop.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var Id = (long) authentication.getPrincipal();
        var user = userRepository.findById(Id).orElse(null);
        return user;
    }
}
