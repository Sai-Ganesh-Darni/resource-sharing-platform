package com.example.resourcesharingsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequest {
    private String refreshToken;
}
