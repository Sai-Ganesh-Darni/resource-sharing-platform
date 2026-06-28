package com.example.resourcesharingsystem.controller;

import com.example.resourcesharingsystem.dto.ResourceResponse;
import com.example.resourcesharingsystem.dto.UserInfo;
import com.example.resourcesharingsystem.service.ResourceService;
import com.example.resourcesharingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final ResourceService resourceService;
    private final UserService userService;


    @GetMapping("/")
    public List<UserInfo> getAllUsers(){
        return userService.getAllUsers();
    }


    @GetMapping("/{userId}/resources/")
    public List<ResourceResponse> getResourceByUserId(@PathVariable Long userId) {
        return resourceService.getAllResourceByUserId(userId);
    }

}
