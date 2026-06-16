package com.example.resourcesharingsystem.exception;

public class ResourceCategoryAlreadyExists extends RuntimeException {
    public ResourceCategoryAlreadyExists(String message) {
        super(message);
    }
}
