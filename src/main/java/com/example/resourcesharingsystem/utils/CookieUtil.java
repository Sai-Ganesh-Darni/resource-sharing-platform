package com.example.resourcesharingsystem.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class CookieUtil {

    @Value("${app.secrets.jwt.expiration}")
    private long jwtExpiration;

    @Value("${app.secrets.refresh-token.expiration}")
    private long refreshTokenExpirationInDays;

    public ResponseCookie createAccessTokenCookie(String accessToken) {
        return ResponseCookie.from(
                    "access_token",
                    accessToken
                )
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMillis(jwtExpiration))
                .build();
    }


    public ResponseCookie createRefreshTokenCookie(
            String token
    ) {

        return ResponseCookie.from(
                        "refresh_token",
                        token
                )
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofDays(refreshTokenExpirationInDays))
                .build();
    }

    public ResponseCookie clearCookie(
            String name
    ) {

        return ResponseCookie.from(
                        name,
                        ""
                )
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
    }

    public String getCookieValue(
            HttpServletRequest request,
            String cookieName
    ) {

        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {

            if (cookieName.equals(
                    cookie.getName()
            )) {

                return cookie.getValue();
            }
        }

        return null;
    }
}
