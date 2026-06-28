package com.example.resourcesharingsystem.controller;

import com.example.resourcesharingsystem.dto.AddResourceCategory;
import com.example.resourcesharingsystem.dto.Response;
import com.example.resourcesharingsystem.service.ResourceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ResourceCategoryService resourceCategoryService;


    @PostMapping("/resources/categories")
    public ResponseEntity<Response> addResourceCategory(@RequestBody AddResourceCategory addResourceCategory) {
        resourceCategoryService.addResourceCategory(addResourceCategory);
        Response response = new Response("Resource Category Created Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
