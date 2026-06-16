package com.example.resourcesharingsystem.resource.service;

import com.example.resourcesharingsystem.exception.ResourceCategoryAlreadyExists;
import com.example.resourcesharingsystem.resource.dto.AddResourceCategory;
import com.example.resourcesharingsystem.resource.entity.ResourceCategory;
import com.example.resourcesharingsystem.resource.repository.ResourceCategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ResourceCategoryService {

    private final ResourceCategoryRepository resourceCategoryRepository;

    public ResourceCategoryService(ResourceCategoryRepository resourceCategoryRepository) {
        this.resourceCategoryRepository = resourceCategoryRepository;
    }

    @Transactional
    public void addResourceCategory(AddResourceCategory addResourceCategory) {

         if(resourceCategoryRepository.existsByName(addResourceCategory.getName()))
             throw new ResourceCategoryAlreadyExists("Resource Category Already Exists");

        ResourceCategory resourceCategory = ResourceCategory.builder()
                .name(addResourceCategory.getName())
                .description(addResourceCategory.getDescription())
                .build();
        resourceCategoryRepository.save(resourceCategory);
    }
}
