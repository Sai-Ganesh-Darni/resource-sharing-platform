package com.example.resourcesharingsystem.controller;

import com.example.resourcesharingsystem.repository.RefreshTokenRepository;
import com.example.resourcesharingsystem.utils.CookieUtil;
import com.example.resourcesharingsystem.exception.InvalidRefreshTokenException;
import com.example.resourcesharingsystem.dto.AddResourceRequest;
import com.example.resourcesharingsystem.dto.ResourceResponse;
import com.example.resourcesharingsystem.service.ResourceService;
import com.example.resourcesharingsystem.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final CookieUtil cookieUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ResourceService resourceService;

    @PostMapping("/")
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

    @GetMapping("/")
    public List<ResourceResponse> getAllResources(){
        return resourceService.getAllResources();
    }
}
