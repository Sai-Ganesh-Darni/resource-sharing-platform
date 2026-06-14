package com.example.resourcesharingsystem.auth.service;


import com.example.resourcesharingsystem.auth.dto.LoginRequest;
import com.example.resourcesharingsystem.auth.dto.LoginResponse;
import com.example.resourcesharingsystem.auth.dto.SignUpRequest;
import com.example.resourcesharingsystem.auth.entity.RefreshToken;
import com.example.resourcesharingsystem.exception.InvalidCredentialsException;
import com.example.resourcesharingsystem.exception.UserAlreadyExistsException;
import com.example.resourcesharingsystem.user.entity.User;
import com.example.resourcesharingsystem.user.repsitory.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokensService refreshTokensService;

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

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository
                .findByEmail(loginRequest.getEmail());

        if(user == null){
            throw new InvalidCredentialsException("No user found with the email");
        }

        boolean matches = passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash());

        if(!matches) {
            throw new InvalidCredentialsException("Wrong password");
        }

        String token = jwtService.generateToken(user.getEmail());

        RefreshToken refreshToken = refreshTokensService.createToken(user);

        return LoginResponse.builder()
                .refreshToken(refreshToken.getToken())
                .accessToken(token)
                .build();

    }
}
