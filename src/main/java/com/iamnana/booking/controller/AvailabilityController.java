package com.iamnana.booking.controller;

import com.iamnana.booking.dto.AvailabilityResponse;
import com.iamnana.booking.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/availability")
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    /*
    It answers the question of which slots are available for a particular
    business service offering.
     */
    @GetMapping("/businesses/{businessId}/services/{serviceId}")
    public ResponseEntity<AvailabilityResponse> getAvailabilityTime(
            @PathVariable Long businessId,
            @PathVariable Long serviceId,
            @RequestParam LocalDate date) {
               AvailabilityResponse availabilityResponse =
                       availabilityService.getAvailabilityTime(businessId,serviceId, date);

               return ResponseEntity.ok(availabilityResponse);
    }
}
