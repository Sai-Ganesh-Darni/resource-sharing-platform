package com.example.resourcesharingsystem.controller;

import com.example.resourcesharingsystem.dto.AddResourceCategory;
import com.example.resourcesharingsystem.dto.ResourceCategoryResponse;
import com.example.resourcesharingsystem.dto.ResourceResponse;
import com.example.resourcesharingsystem.dto.Response;
import com.example.resourcesharingsystem.service.ResourceCategoryService;
import com.example.resourcesharingsystem.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/resource-categories")
@RequiredArgsConstructor
public class ResourceCategoryController {

    private final ResourceCategoryService resourceCategoryService;
    private final ResourceService resourceService;

    @GetMapping("/")
    public List<ResourceCategoryResponse> getResourceCategories(){
        return resourceCategoryService.getResourceCategories();
    }

    @GetMapping("/{categoryId}/resources")
    public List<ResourceResponse> getAllResourcesByCategory(@PathVariable Long categoryId){
        return resourceService.getAllResourcesByCategory(categoryId);
    }
}
