package com.iamnana.booking.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class AvailabilitySlotResponse {
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean available;
}
