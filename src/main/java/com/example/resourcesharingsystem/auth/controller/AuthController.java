package com.example.resourcesharingsystem.auth.controller;

import com.example.resourcesharingsystem.auth.dto.AuthResponse;
import com.example.resourcesharingsystem.auth.dto.SignUpRequest;
import com.example.resourcesharingsystem.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public AuthResponse signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        System.out.println("Hello World");
        authService.handleSignup(signUpRequest);
        return new AuthResponse("User signed up successfully");
    }
}
