package com.example.resourcesharingsystem.mapper;

import com.example.resourcesharingsystem.dto.BookingResponse;
import com.example.resourcesharingsystem.entity.Booking;

public class BookingMapper {
    public static BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getId())
                .requestedBy(booking.getRequestedBy().getId())
                .resourceId(booking.getResource().getId())
                .bookingStatus(booking.getBookingStatus())
                .bidAmount(booking.getBidAmount())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .build();
    }
}
