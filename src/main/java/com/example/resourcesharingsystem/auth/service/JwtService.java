package com.example.resourcesharingsystem.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${app.secrets.jwt.secret}")
    private String jwtSecretKey;

    @Value("${app.secrets.jwt.expiration}")
    private long expirationTime;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .signWith(getSecretKey())
                .issuedAt(new java.util.Date())
                .expiration( new java.util.Date(System.currentTimeMillis() + expirationTime))
                .compact();
    }

}
