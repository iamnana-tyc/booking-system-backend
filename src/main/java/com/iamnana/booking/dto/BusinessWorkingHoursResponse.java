package com.iamnana.booking.dto;

import com.iamnana.booking.entity.DayOfWeek;
import lombok.Data;

import java.time.LocalTime;

@Data
public class BusinessWorkingHoursResponse {
    private DayOfWeek dayOfWeek;
    private LocalTime openTime;
    private LocalTime closeTime;
}
