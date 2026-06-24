package com.example.resourcesharingsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResourceResponse {
    private long id;

    private String name;

    private String description;

    private int categoryId;

    private String categoryName;

    private long ownedBy;

    private String ownedByName;

    private double value;

    private int currentStatus;

}
