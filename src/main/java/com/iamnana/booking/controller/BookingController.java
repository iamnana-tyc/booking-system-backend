package com.iamnana.booking.controller;

import com.iamnana.booking.dto.BookingRequest;
import com.iamnana.booking.dto.BookingResponse;
import com.iamnana.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/businesses/{businessId}/services/{serviceId}/bookings")
    public ResponseEntity<BookingResponse> createBooking(
            @PathVariable Long businessId,
            @PathVariable Long serviceId,
            @Valid @RequestBody BookingRequest request){
        BookingResponse booking = bookingService.createBooking(businessId, serviceId, request);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/businesses/{businessId}/bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookingsForBusiness(@PathVariable Long businessId){
        List<BookingResponse> bookings = bookingService.getAllBookingsForBusiness(businessId);

        return ResponseEntity.ok(bookings);
    }

    @PatchMapping("/businesses/{businessId}/bookings/{bookingId}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(@PathVariable Long businessId, @PathVariable Long bookingId){
        BookingResponse response = bookingService.confirmBooking(businessId, bookingId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
