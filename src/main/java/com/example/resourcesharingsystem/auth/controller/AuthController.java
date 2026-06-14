package com.example.resourcesharingsystem.auth.controller;

import com.example.resourcesharingsystem.auth.dto.AuthResponse;
import com.example.resourcesharingsystem.auth.dto.LoginRequest;
import com.example.resourcesharingsystem.auth.dto.LoginResponse;
import com.example.resourcesharingsystem.auth.dto.SignUpRequest;
import com.example.resourcesharingsystem.auth.service.AuthService;
import com.example.resourcesharingsystem.auth.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @PostMapping("/signup")
    public AuthResponse signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        System.out.println("Hello World");
        authService.handleSignup(signUpRequest);
        return new AuthResponse("User signed up successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        LoginResponse loginResponse = authService.login(loginRequest);

        httpServletResponse.addHeader(
                HttpHeaders.SET_COOKIE,
                cookieUtil.createAccessTokenCookie(loginResponse.getAccessToken()).toString()
        );

        httpServletResponse.addHeader(
                HttpHeaders.SET_COOKIE,
                cookieUtil.createRefreshTokenCookie(loginResponse.getRefreshToken()).toString()
        );

        return ResponseEntity.ok(
                new AuthResponse("User logged in successfully")
        );
    }
}
