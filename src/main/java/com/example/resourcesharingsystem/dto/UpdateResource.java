package com.example.resourcesharingsystem.dto;

import com.example.resourcesharingsystem.entity.ResourceCategory;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResource {
    private Long id;
    private String resourceName;
    private String description;
    private double value;
    private int currentStatus;
    private int categoryId;
}
