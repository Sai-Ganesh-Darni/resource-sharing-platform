package com.example.resourcesharingsystem.service;

import com.example.resourcesharingsystem.dto.BookingResponse;
import com.example.resourcesharingsystem.dto.UpdateResource;
import com.example.resourcesharingsystem.entity.*;
import com.example.resourcesharingsystem.exception.NotFoundException;
import com.example.resourcesharingsystem.dto.AddResourceRequest;
import com.example.resourcesharingsystem.dto.ResourceResponse;
import com.example.resourcesharingsystem.exception.UnAuthorizedException;
import com.example.resourcesharingsystem.mapper.BookingMapper;
import com.example.resourcesharingsystem.mapper.ResourceMapper;
import com.example.resourcesharingsystem.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {
    private final UserRepository userRepository;
    private final ResourceCategoryRepository resourceCategoryRepository;
    private final ResourceRepository resourceRepository;
    private final AuthService authService;
    private final RoleRepository roleRepository;
    private final BookingRepository bookingRepository;

    public ResourceService(UserRepository userRepository, ResourceCategoryRepository resourceCategoryRepository, ResourceRepository resourceRepository, AuthService authService, RoleRepository roleRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.resourceCategoryRepository = resourceCategoryRepository;
        this.resourceRepository = resourceRepository;
        this.authService = authService;
        this.roleRepository = roleRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public ResourceResponse addResource(AddResourceRequest addResourceRequest) {

        User owner = userRepository.findById(addResourceRequest.getOwnedBy());

        if(owner == null) {
            throw new NotFoundException("User not found");
        }

        ResourceCategory resourceCategory = resourceCategoryRepository.findById(addResourceRequest.getCategoryId());
        if(resourceCategory == null) {
            throw new NotFoundException("Resource category not found");
        }

        Resource resource = Resource.builder()
                .name(addResourceRequest.getName())
                .description(addResourceRequest.getDescription())
                .currentStatus(1)
                .owner(owner)
                .value(addResourceRequest.getValue())
                .createdBy(addResourceRequest.getLoginUser().getId())
                .updatedBy(addResourceRequest.getLoginUser().getId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .category(resourceCategory)
                .build();

        Resource createdResource = resourceRepository.save(resource);

        return ResourceMapper.toResponse(createdResource);
    }

    @Transactional
    public List<ResourceResponse> getAllResources(int pageSize, int pageNumber) {
        List<Resource> resources = resourceRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
//        List<Resource> resources = resourceRepository.findAll();
        List<ResourceResponse> resourceResponseList = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceResponse resourceResponse = ResourceMapper.toResponse(resource);
            resourceResponseList.add(resourceResponse);
        }
        return resourceResponseList;
    }

    public List<ResourceResponse> getAllResourceByUserId(Long userId){
        User owner = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        List<Resource> resources = resourceRepository.findAllByOwner(owner);
        List<ResourceResponse> resourceResponseList = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceResponse resourceResponse = ResourceMapper.toResponse(resource);
            resourceResponseList.add(resourceResponse);
        }
        return resourceResponseList;
    }

    public ResourceResponse getResourceById(Long resourceId){
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("Resource not found"));
        return ResourceMapper.toResponse(resource);
    }

    public List<ResourceResponse> getAllResourcesByCategory(Long categoryId) {
        ResourceCategory resourceCategory = resourceCategoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
        List<Resource> resources = resourceRepository.findAllByCategory(resourceCategory);
        List<ResourceResponse> resourceResponseList = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceResponse resourceResponse = ResourceMapper.toResponse(resource);
            resourceResponseList.add(resourceResponse);
        }
        return resourceResponseList;
    }

    public UpdateResource updateResource(UpdateResource updateResource, HttpServletRequest request) {
        Resource resource = resourceRepository.findById(updateResource.getId()).orElseThrow(()  -> new NotFoundException("Resource not found"));
        User user = authService.getLoginUser(request);
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new NotFoundException("Role not found"));

        if(!resource.getOwner().getId().equals(user.getId()) || !user.getRoles().contains(adminRole)) {
            throw new UnAuthorizedException("You are not allowed to update this resource");
        }

        resource.setName(updateResource.getResourceName());
        resource.setDescription(updateResource.getDescription());
        resource.setValue(updateResource.getValue());
        resource.setCurrentStatus(updateResource.getCurrentStatus());

        ResourceCategory resourceCategory = resourceCategoryRepository.findById(updateResource.getCategoryId());
        if(resourceCategory == null) {
            throw new NotFoundException("Resource category not found");
        }
        resource.setCategory(resourceCategory);

        resource = resourceRepository.save(resource);

        return ResourceMapper.toUpdateResource(resource);
    }

    public List<BookingResponse> getBookings(long resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("Resource not found"));
        List<Booking> bookings = bookingRepository.findAllByResource(resource);
        List<BookingResponse> bookingResponseList = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingResponse bookingResponse = BookingMapper.toResponse(booking);
            bookingResponseList.add(bookingResponse);
        }
        return bookingResponseList;
    }
}
