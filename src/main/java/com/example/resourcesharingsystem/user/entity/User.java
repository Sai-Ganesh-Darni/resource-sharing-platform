package com.example.resourcesharingsystem.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String address;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String gender;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "google_id")
    private String googleId;
    @Column(name = "phone_verified")
    private boolean phoneVerified;

    @Column(name = "auth_provider")
    private String authProvider;

}
