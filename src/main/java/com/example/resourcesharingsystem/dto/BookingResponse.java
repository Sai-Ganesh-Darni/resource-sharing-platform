package com.example.resourcesharingsystem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {
    private Long bookingId;
    private Long resourceId;
    private Long requestedBy;
    private double bidAmount;
    private int bookingStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
