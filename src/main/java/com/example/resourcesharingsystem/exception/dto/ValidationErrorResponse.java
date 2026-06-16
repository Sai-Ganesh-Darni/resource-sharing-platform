package com.example.resourcesharingsystem.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ValidationErrorResponse {
    private String message;

    @Builder.Default
    private String errorType = "ValidationError";

    private Map<String, String> errors;
}
