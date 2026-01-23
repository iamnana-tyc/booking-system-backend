package com.iamnana.booking.dto;

import com.iamnana.booking.entity.DayOfWeek;
import lombok.Data;

import java.time.LocalTime;

@Data
public class BusinessWorkingHoursPatchRequest {
    private DayOfWeek dayOfWeek;
    private LocalTime openTime;
    private LocalTime closeTime;
}
