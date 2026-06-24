package com.example.resourcesharingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "resource")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String description;

    @JoinColumn(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ResourceCategory category;

    private int currentStatus;
    private double value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JoinColumn(name = "owned_by")
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    private long createdBy;
    private long updatedBy;
}
