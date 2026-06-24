package com.example.resourcesharingsystem.mapper;

import com.example.resourcesharingsystem.dto.ResourceCategoryResponse;
import com.example.resourcesharingsystem.entity.ResourceCategory;

public class ResourceCategoryMapper {
    public static ResourceCategoryResponse toResponse(ResourceCategory resourceCategory) {
        return ResourceCategoryResponse.builder()
                .id(resourceCategory.getId())
                .categoryName(resourceCategory.getName())
                .description(resourceCategory.getDescription())
                .build();
    }
}
