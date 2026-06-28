package com.example.resourcesharingsystem.service;

import com.example.resourcesharingsystem.dto.ResourceCategoryResponse;
import com.example.resourcesharingsystem.exception.AlreadyExistsException;
import com.example.resourcesharingsystem.dto.AddResourceCategory;
import com.example.resourcesharingsystem.entity.ResourceCategory;
import com.example.resourcesharingsystem.mapper.ResourceCategoryMapper;
import com.example.resourcesharingsystem.repository.ResourceCategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceCategoryService {

    private final ResourceCategoryRepository resourceCategoryRepository;

    public ResourceCategoryService(ResourceCategoryRepository resourceCategoryRepository) {
        this.resourceCategoryRepository = resourceCategoryRepository;
    }

    @Transactional
    public void addResourceCategory(AddResourceCategory addResourceCategory) {

         if(resourceCategoryRepository.existsByName(addResourceCategory.getName()))
             throw new AlreadyExistsException("Resource Category Already Exists");

        ResourceCategory resourceCategory = ResourceCategory.builder()
                .name(addResourceCategory.getName())
                .description(addResourceCategory.getDescription())
                .build();
        resourceCategoryRepository.save(resourceCategory);
    }

    @Transactional
    public List<ResourceCategoryResponse> getResourceCategories(){
        List<ResourceCategory> resourceCategoryList = resourceCategoryRepository.findAllByOrderByNameAsc();
        List<ResourceCategoryResponse> resourceCategoryResponses = new ArrayList<>();
        for(ResourceCategory resourceCategory : resourceCategoryList){
            resourceCategoryResponses.add(ResourceCategoryMapper.toResponse(resourceCategory));
        }
        return resourceCategoryResponses;
    }
}
