package com.example.resourcesharingsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResourceCategoryResponse {
    public int id;
    public String categoryName;
    public String description;

}
