package com.example.resourcesharingsystem.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.resourcesharingsystem.filter.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
    private  JwtAuthenticationFilter jwtFilter;

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
                                        "/admin/doc/**",
                                        "/admin/openapi/**"
                                ).permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()

                        )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );;
        return http.build();
    }
}
