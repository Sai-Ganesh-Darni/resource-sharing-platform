package com.example.resourcesharingsystem.service;

import com.example.resourcesharingsystem.exception.UserNotFoundException;
import com.example.resourcesharingsystem.dto.AddResourceRequest;
import com.example.resourcesharingsystem.dto.ResourceResponse;
import com.example.resourcesharingsystem.entity.Resource;
import com.example.resourcesharingsystem.entity.ResourceCategory;
import com.example.resourcesharingsystem.mapper.ResourceMapper;
import com.example.resourcesharingsystem.repository.ResourceCategoryRepository;
import com.example.resourcesharingsystem.repository.ResourceRepository;
import com.example.resourcesharingsystem.entity.User;
import com.example.resourcesharingsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<ResourceResponse> getAllResources(){
        List<Resource> resources = resourceRepository.findAll();
        List<ResourceResponse> resourceResponseList = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceResponse resourceResponse = ResourceMapper.toResponse(resource);
            resourceResponseList.add(resourceResponse);
        }
        return resourceResponseList;
    }
}
