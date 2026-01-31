package com.iamnana.booking.dto;

import com.iamnana.booking.entity.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingResponse {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private BookingStatus status;
}
