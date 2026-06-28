package com.example.resourcesharingsystem.mapper;

import com.example.resourcesharingsystem.dto.ResourceResponse;
import com.example.resourcesharingsystem.dto.UpdateResource;
import com.example.resourcesharingsystem.entity.Resource;

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

    public static UpdateResource toUpdateResource(Resource resource) {
        return UpdateResource.builder()
                .id(resource.getId())
                .resourceName(resource.getName())
                .description(resource.getDescription())
                .value(resource.getValue())
                .categoryId(resource.getCategory().getId())
                .currentStatus(resource.getCurrentStatus())
                .build();
    }
}
