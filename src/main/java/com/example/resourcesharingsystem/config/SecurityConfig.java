package com.example.resourcesharingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy((
                                SessionCreationPolicy.STATELESS
                        ))
                )
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers("/").permitAll()
                                .requestMatchers(
                                        "/api/admin/doc/**",
                                        "/api/admin/openapi/**"
                                ).permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                        );
        return http.build();
    }
}
