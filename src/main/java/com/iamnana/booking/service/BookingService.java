package com.iamnana.booking.service;

import com.iamnana.booking.dto.BookingRequest;
import com.iamnana.booking.dto.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(Long businessId, Long serviceId, BookingRequest request);

    List<BookingResponse> getAllBookingsForBusiness(Long businessId);

    BookingResponse confirmBooking(Long businessId, Long bookingId);
}
