package com.iamnana.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingRequest {
    @NotNull(message = "The date for booking is required")
    private LocalDate date;

    @NotNull(message = "Time for booking is required")
    private LocalTime startTime;
}
