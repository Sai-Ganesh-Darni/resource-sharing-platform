package com.example.resourcesharingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private String legalName;
    private String email;
    private String mobile;
    private int userState;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    private boolean deleted;
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

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet();

}
