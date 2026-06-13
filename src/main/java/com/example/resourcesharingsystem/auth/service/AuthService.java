package com.example.resourcesharingsystem.auth.service;


import com.example.resourcesharingsystem.auth.dto.SignUpRequest;
import com.example.resourcesharingsystem.exception.UserAlreadyExistsException;
import com.example.resourcesharingsystem.user.entity.User;
import com.example.resourcesharingsystem.user.repsitory.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void handleSignup(SignUpRequest signUpRequest) {
        // Check if the user exists with the same email or mobile
        if(userRepository.existsByEmail(signUpRequest.getEmail()) || userRepository.existsByMobile(signUpRequest.getMobile())){
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .mobile(signUpRequest.getMobile())
                .passwordHash(passwordEncoder.encode(signUpRequest.getPassword()))
                .address(signUpRequest.getAddress())
                .emailVerified(false)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
}
