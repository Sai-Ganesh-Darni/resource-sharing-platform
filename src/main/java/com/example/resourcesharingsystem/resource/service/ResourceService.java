package com.example.resourcesharingsystem.resource.service;

import com.example.resourcesharingsystem.exception.UserNotFoundException;
import com.example.resourcesharingsystem.resource.dto.AddResourceRequest;
import com.example.resourcesharingsystem.resource.dto.ResourceResponse;
import com.example.resourcesharingsystem.resource.entity.Resource;
import com.example.resourcesharingsystem.resource.entity.ResourceCategory;
import com.example.resourcesharingsystem.resource.mapper.ResourceMapper;
import com.example.resourcesharingsystem.resource.repository.ResourceCategoryRepository;
import com.example.resourcesharingsystem.resource.repository.ResourceRepository;
import com.example.resourcesharingsystem.user.entity.User;
import com.example.resourcesharingsystem.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ResourceService {
    private final UserRepository userRepository;
    private final ResourceCategoryRepository resourceCategoryRepository;
    private final ResourceRepository resourceRepository;

    public ResourceService(UserRepository userRepository, ResourceCategoryRepository resourceCategoryRepository, ResourceRepository resourceRepository) {
        this.userRepository = userRepository;
        this.resourceCategoryRepository = resourceCategoryRepository;
        this.resourceRepository = resourceRepository;
    }

    @Transactional
    public ResourceResponse addResource(AddResourceRequest addResourceRequest) {

        User owner = userRepository.findById(addResourceRequest.getOwnedBy());

        if(owner == null) {
            throw new UserNotFoundException("User not found");
        }

        ResourceCategory resourceCategory = resourceCategoryRepository.findById(addResourceRequest.getCategoryId());

        Resource resource = Resource.builder()
                .name(addResourceRequest.getName())
                .description(addResourceRequest.getDescription())
                .currentStatus(1)
                .owner(owner)
                .value(addResourceRequest.getValue())
                .createdBy(addResourceRequest.getLoginUser().getId())
                .updatedBy(addResourceRequest.getLoginUser().getId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .category(resourceCategory)
                .build();

        Resource createdResource = resourceRepository.save(resource);

        return ResourceMapper.toResponse(createdResource);
    }
}
