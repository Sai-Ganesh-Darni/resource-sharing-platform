package com.example.resourcesharingsystem.controller;

import com.example.resourcesharingsystem.dto.BookingResponse;
import com.example.resourcesharingsystem.dto.UpdateResource;
import com.example.resourcesharingsystem.repository.RefreshTokenRepository;
import com.example.resourcesharingsystem.service.AuthService;
import com.example.resourcesharingsystem.service.BookingService;
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

    private final RefreshTokenRepository refreshTokenRepository;
    private final ResourceService resourceService;
    private final AuthService authService;
    private final BookingService bookingService;

    @PostMapping
    @Valid
    public ResponseEntity<ResourceResponse>  createResource(@RequestBody AddResourceRequest addResourceRequest, HttpServletRequest request) {

        User user = authService.getLoginUser(request);
        addResourceRequest.setLoginUser(user);

        ResourceResponse resourceResponse = resourceService.addResource(addResourceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(resourceResponse);
    }

    @GetMapping
    public List<ResourceResponse> getAllResources(@RequestParam(required = false, defaultValue = "5") int pageSize, @RequestParam(required = false, defaultValue = "1") int pageNumber) {
        return resourceService.getAllResources(pageSize, pageNumber-1);
    }


    @GetMapping("/{id}")
    public ResourceResponse getResourceById(@PathVariable Long id){
        return resourceService.getResourceById(id);
    }

    @PatchMapping
    public UpdateResource updateResource(@RequestBody UpdateResource updateResource, HttpServletRequest request) {
        return resourceService.updateResource(updateResource, request);
    }

    @GetMapping("/{resourceId}/bookings")
    public List<BookingResponse> getBookings(@PathVariable Long resourceId){
        return resourceService.getBookings(resourceId);
    }
}
