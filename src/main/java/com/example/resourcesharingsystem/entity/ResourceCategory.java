package com.example.resourcesharingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resource_category")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

}
