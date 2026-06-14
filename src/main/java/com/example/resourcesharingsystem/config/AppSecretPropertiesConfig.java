package com.example.resourcesharingsystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.secrets")
@Getter
@Setter
public class AppSecretPropertiesConfig {

    private Jwt jwt = new Jwt();

    private RefreshToken refreshToken = new RefreshToken();

    @Getter
    @Setter
    public static class Jwt {
        private String secret;
        private Long expiration;
    }

    @Getter
    @Setter
    public static class RefreshToken {
        private Long expiration;
    }

}