package com.example.resourcesharingsystem.controller;

import com.example.resourcesharingsystem.dto.BookingRequest;
import com.example.resourcesharingsystem.dto.BookingResponse;
import com.example.resourcesharingsystem.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/")
    public ResponseEntity<BookingResponse> booking(@RequestBody @Valid BookingRequest bookingRequest, HttpServletRequest request) {
        BookingResponse bookingResponse = bookingService.bookResource(bookingRequest);
        URI bookingURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build().toUri();
        return ResponseEntity.created(bookingURI).body(bookingResponse);
    }

    @GetMapping("/")
    public List<BookingResponse> getBookings(){
        return bookingService.getBookings();
    }
}
