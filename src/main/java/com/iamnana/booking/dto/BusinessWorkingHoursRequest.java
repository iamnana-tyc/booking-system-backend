package com.iamnana.booking.dto;

import com.iamnana.booking.entity.DayOfWeek;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class BusinessWorkingHoursRequest {
    @NotNull(message = "Day of the week is required")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Open time is required")
    private LocalTime openTime;

    @NotNull(message = "Close time is required")
    private LocalTime closeTime;
}
