package com.example.resourcesharingsystem.dto;

import com.example.resourcesharingsystem.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddResourceRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private int categoryId;
    @NotEmpty
    private long ownedBy;
    @NotEmpty
    private double value;

    private User loginUser;
}
