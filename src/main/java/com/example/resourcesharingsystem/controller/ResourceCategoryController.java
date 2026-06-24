package com.example.resourcesharingsystem.controller;

import com.example.resourcesharingsystem.dto.AddResourceCategory;
import com.example.resourcesharingsystem.dto.ResourceCategoryResponse;
import com.example.resourcesharingsystem.dto.Response;
import com.example.resourcesharingsystem.service.ResourceCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/admin/resource-categories")
public class ResourceCategoryController {

    private final ResourceCategoryService resourceCategoryService;

    public ResourceCategoryController(ResourceCategoryService resourceCategoryService) {
        this.resourceCategoryService = resourceCategoryService;
    }

    @PostMapping("/")
    public ResponseEntity<Response> addResourceCategory(@RequestBody AddResourceCategory addResourceCategory) {
        resourceCategoryService.addResourceCategory(addResourceCategory);
        Response response = new Response("Resource Category Created Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public List<ResourceCategoryResponse> getResourceCategories(){
        return resourceCategoryService.getResourceCategories();
    }
}
