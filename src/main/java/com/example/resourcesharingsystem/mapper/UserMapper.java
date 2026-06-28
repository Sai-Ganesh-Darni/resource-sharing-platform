package com.example.resourcesharingsystem.mapper;

import com.example.resourcesharingsystem.dto.UserInfo;
import com.example.resourcesharingsystem.entity.User;


public class UserMapper {
    public static UserInfo toResponse(User user) {
        return UserInfo.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .gender(user.getGender())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
