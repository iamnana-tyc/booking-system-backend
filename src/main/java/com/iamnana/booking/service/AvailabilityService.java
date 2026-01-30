package com.iamnana.booking.service;

import com.iamnana.booking.dto.AvailabilityResponse;

import java.time.LocalDate;

public interface AvailabilityService {
    AvailabilityResponse getAvailabilityTime(Long businessId,  Long serviceId, LocalDate date);
}
