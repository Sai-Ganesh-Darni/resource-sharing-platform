package com.example.resourcesharingsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserInfo {
    private Long userId;
    private String name;
    private String email;
    private String mobile;
    private String gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
