package com.iamnana.booking.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AvailabilityResponse {
    private LocalDate date;
    private List<AvailabilitySlotResponse> slots;
}
