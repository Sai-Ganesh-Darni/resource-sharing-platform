package com.example.resourcesharingsystem.service;


import com.example.resourcesharingsystem.dto.LoginRequest;
import com.example.resourcesharingsystem.dto.LoginResponse;
import com.example.resourcesharingsystem.dto.SignUpRequest;
import com.example.resourcesharingsystem.entity.RefreshToken;
import com.example.resourcesharingsystem.exception.AlreadyExistsException;
import com.example.resourcesharingsystem.exception.InvalidRefreshTokenException;
import com.example.resourcesharingsystem.exception.NotFoundException;
import com.example.resourcesharingsystem.repository.RefreshTokenRepository;
import com.example.resourcesharingsystem.repository.RoleRepository;
import com.example.resourcesharingsystem.exception.InvalidCredentialsException;
import com.example.resourcesharingsystem.entity.Role;
import com.example.resourcesharingsystem.entity.User;
import com.example.resourcesharingsystem.repository.UserRepository;

import com.example.resourcesharingsystem.utils.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CookieUtil cookieUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokensService refreshTokensService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RoleRepository roleRepository;

    public void handleSignup(SignUpRequest signUpRequest) {
        // Check if the user exists with the same email or mobile
        if(userRepository.existsByEmail(signUpRequest.getEmail()) || userRepository.existsByMobile(signUpRequest.getMobile())){
            throw new AlreadyExistsException("User already exists");
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

        Role role = roleRepository.findByName("ROLE_CUSTOMER").orElseThrow(() -> new NotFoundException("Role not found"));
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

    public User getLoginUser(HttpServletRequest request) {
        String refreshToken =
                cookieUtil.getCookieValue(
                        request,
                        "refresh_token"
                );
        return refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token")).getUser();
    }
}
