package com.example.resourcesharingsystem.resource.controller;

import com.example.resourcesharingsystem.auth.entity.RefreshToken;
import com.example.resourcesharingsystem.auth.repository.RefreshTokenRepository;
import com.example.resourcesharingsystem.auth.service.AuthService;
import com.example.resourcesharingsystem.auth.util.CookieUtil;
import com.example.resourcesharingsystem.exception.InvalidRefreshTokenException;
import com.example.resourcesharingsystem.resource.dto.AddResourceRequest;
import com.example.resourcesharingsystem.resource.dto.ResourceResponse;
import com.example.resourcesharingsystem.resource.dto.Response;
import com.example.resourcesharingsystem.resource.service.ResourceService;
import com.example.resourcesharingsystem.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resource")
@RequiredArgsConstructor
public class ResourceController {

    private final CookieUtil cookieUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ResourceService resourceService;

    @PostMapping("/create")
    @Valid
    public ResponseEntity<ResourceResponse>  createResource(@RequestBody AddResourceRequest addResourceRequest, HttpServletRequest request) {
        String refreshToken =
                cookieUtil.getCookieValue(
                        request,
                        "refresh_token"
                );
        User user = refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token")).getUser();

        addResourceRequest.setLoginUser(user);

        ResourceResponse resourceResponse = resourceService.addResource(addResourceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(resourceResponse);
    }
}
