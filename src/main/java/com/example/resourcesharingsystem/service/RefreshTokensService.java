package com.example.resourcesharingsystem.service;

import com.example.resourcesharingsystem.entity.RefreshToken;
import com.example.resourcesharingsystem.repository.RefreshTokenRepository;
import com.example.resourcesharingsystem.exception.InvalidRefreshTokenException;
import com.example.resourcesharingsystem.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokensService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.secrets.refresh-token.expiration}")
    private long refreshTokenExpirationDays;

    public RefreshToken createToken(User user){
        refreshTokenRepository.deleteByUserId(user.getId());

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(refreshTokenExpirationDays))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validateToken(String token){
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));

        if(refreshToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        return refreshToken;
    }
}
