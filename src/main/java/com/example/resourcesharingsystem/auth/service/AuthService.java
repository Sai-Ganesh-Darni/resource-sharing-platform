package com.example.resourcesharingsystem.auth.service;


import com.example.resourcesharingsystem.auth.dto.LoginRequest;
import com.example.resourcesharingsystem.auth.dto.LoginResponse;
import com.example.resourcesharingsystem.auth.dto.SignUpRequest;
import com.example.resourcesharingsystem.auth.entity.RefreshToken;
import com.example.resourcesharingsystem.auth.repository.RefreshTokenRepository;
import com.example.resourcesharingsystem.auth.repository.RoleRepository;
import com.example.resourcesharingsystem.exception.InvalidCredentialsException;
import com.example.resourcesharingsystem.exception.RoleNotFoundException;
import com.example.resourcesharingsystem.exception.UserAlreadyExistsException;
import com.example.resourcesharingsystem.auth.entity.Role;
import com.example.resourcesharingsystem.user.entity.User;
import com.example.resourcesharingsystem.user.repository.UserRepository;

import jakarta.transaction.Transactional;
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
    private final RefreshTokenRepository refreshTokenRepository;
    private final RoleRepository roleRepository;

    public void handleSignup(SignUpRequest signUpRequest) {
        // Check if the user exists with the same email or mobile
        if(userRepository.existsByEmail(signUpRequest.getEmail()) || userRepository.existsByMobile(signUpRequest.getMobile())){
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = User.builder()
                .name(signUpRequest.getName())
                .legalName(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .mobile(signUpRequest.getMobile())
                .passwordHash(passwordEncoder.encode(signUpRequest.getPassword()))
                .emailVerified(false)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Role role = roleRepository.findByName("ROLE_CUSTOMER").orElseThrow(() -> new RoleNotFoundException("Role not found"));
        user.getRoles().add(role);

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

    public String refresh(String token) {
        RefreshToken refreshToken = refreshTokensService.validateToken(token);
        User user = refreshToken.getUser();
        if(user == null){
            throw new InvalidCredentialsException("No user found with the token");
        }

        return jwtService.generateToken(user.getEmail());
    }
}
