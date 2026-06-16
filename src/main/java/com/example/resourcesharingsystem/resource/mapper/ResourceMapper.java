package com.example.resourcesharingsystem.resource.mapper;

import com.example.resourcesharingsystem.resource.dto.ResourceResponse;
import com.example.resourcesharingsystem.resource.entity.Resource;

public class ResourceMapper {
    public static ResourceResponse toResponse(Resource resource) {
        ResourceResponse resourceResponse = ResourceResponse.builder()
                .id(resource.getId())
                .name(resource.getName())
                .description(resource.getDescription())
                .currentStatus(resource.getCurrentStatus())
                .categoryId(resource.getCategory().getId())
                .categoryName(resource.getCategory().getName())
                .ownedBy(resource.getOwner().getId())
                .ownedByName(resource.getOwner().getName())
                .build();
        return resourceResponse;
    }
}
