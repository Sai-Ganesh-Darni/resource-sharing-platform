package com.example.resourcesharingsystem.service;

import com.example.resourcesharingsystem.constants.BookingConstants;
import com.example.resourcesharingsystem.dto.BookingRequest;
import com.example.resourcesharingsystem.dto.BookingResponse;
import com.example.resourcesharingsystem.entity.Booking;
import com.example.resourcesharingsystem.entity.Resource;
import com.example.resourcesharingsystem.entity.User;
import com.example.resourcesharingsystem.exception.NotFoundException;
import com.example.resourcesharingsystem.mapper.BookingMapper;
import com.example.resourcesharingsystem.repository.BookingRepository;
import com.example.resourcesharingsystem.repository.ResourceRepository;
import com.example.resourcesharingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final BookingRepository bookingRepository;


    public BookingResponse bookResource(BookingRequest bookingRequest) {
        User user = userRepository.findById(bookingRequest.getRequestedBy()).orElseThrow(() -> new NotFoundException("User not found"));
        Resource resource = resourceRepository.findById(bookingRequest.getResourceId()).orElseThrow(() -> new NotFoundException("Resource not found"));
        Booking booking = Booking.builder()
                .requestedBy(user)
                .resource(resource)
                .bidAmount(bookingRequest.getBidAmount())
                .bookingStatus(BookingConstants.BOOKING_STATUS_PENDING)
                .startTime(bookingRequest.getStartTime())
                .endTime(bookingRequest.getEndTime())
                .build();

        booking = bookingRepository.save(booking);
        return BookingMapper.toResponse(booking);
    }

    public List<BookingResponse> getBookings(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String email;
            if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername();
            } else {
                email = principal.toString();
            }
            user = userRepository.findByEmail(email);
        }else {
            throw new AuthenticationServiceException("Authentication required");
        }
        List<Booking> bookings = bookingRepository.findAllByRequestedBy(user);
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingResponses.add(BookingMapper.toResponse(booking));
        }
        return bookingResponses;
    }

}
