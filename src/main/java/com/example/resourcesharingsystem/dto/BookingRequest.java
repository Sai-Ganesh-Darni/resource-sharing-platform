package com.example.resourcesharingsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingRequest {
    @NotNull
    private Long resourceId;
    @NotNull
    private Long requestedBy;
    @NotNull
    private double bidAmount;
    private int bookingStatus;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
}
