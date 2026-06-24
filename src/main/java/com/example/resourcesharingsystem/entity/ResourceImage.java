package com.example.resourcesharingsystem.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "resource_images")
public class ResourceImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "resource_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Resource resource;

    private String imageUrl;

    private boolean isPrimary;

    private boolean deleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long createdBy;
    private long updatedBy;
}
