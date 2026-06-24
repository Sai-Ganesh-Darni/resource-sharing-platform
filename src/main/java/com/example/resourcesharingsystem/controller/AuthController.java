package com.example.resourcesharingsystem.controller;

import com.example.resourcesharingsystem.dto.AuthResponse;
import com.example.resourcesharingsystem.dto.LoginRequest;
import com.example.resourcesharingsystem.dto.LoginResponse;
import com.example.resourcesharingsystem.dto.SignUpRequest;
import com.example.resourcesharingsystem.service.AuthService;
import com.example.resourcesharingsystem.utils.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @PostMapping("/signup")
    public AuthResponse signup(@Valid @RequestBody SignUpRequest signUpRequest) {
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

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String refreshToken =
                cookieUtil.getCookieValue(
                        httpServletRequest,
                        "refresh_token"
                );

        String accessToken =
                authService.refresh(
                        refreshToken
                );

        httpServletResponse.addHeader(
                HttpHeaders.SET_COOKIE,
                cookieUtil
                        .createAccessTokenCookie(
                                accessToken
                        )
                        .toString()
        );

        return ResponseEntity.ok(
                new AuthResponse(
                        "Token refreshed"
                )
        );

    }
}
