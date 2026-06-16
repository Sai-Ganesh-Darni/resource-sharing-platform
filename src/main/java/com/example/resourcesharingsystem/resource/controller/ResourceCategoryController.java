package com.example.resourcesharingsystem.resource.controller;

import com.example.resourcesharingsystem.resource.dto.AddResourceCategory;
import com.example.resourcesharingsystem.resource.dto.Response;
import com.example.resourcesharingsystem.resource.entity.Resource;
import com.example.resourcesharingsystem.resource.entity.ResourceCategory;
import com.example.resourcesharingsystem.resource.service.ResourceCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/admin/resource-category")
public class ResourceCategoryController {

    private final ResourceCategoryService resourceCategoryService;

    public ResourceCategoryController(ResourceCategoryService resourceCategoryService) {
        this.resourceCategoryService = resourceCategoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Response> addResourceCategory(@RequestBody AddResourceCategory addResourceCategory) {
        resourceCategoryService.addResourceCategory(addResourceCategory);
        Response response = new Response("Resource Category Created Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
