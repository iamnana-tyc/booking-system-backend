package com.iamnana.booking.controller;

import com.iamnana.booking.dto.BusinessWorkingHoursRequest;
import com.iamnana.booking.dto.BusinessWorkingHoursResponse;
import com.iamnana.booking.service.BusinessWorkingHoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class BusinessWorkingHoursController {

    private final BusinessWorkingHoursService businessWorkingHoursService;

    @PostMapping("/businesses/{businessId}/working-hours")
    public ResponseEntity<BusinessWorkingHoursResponse> createBusinessWorkingHours(
            @PathVariable Long businessId,
            @Valid @RequestBody BusinessWorkingHoursRequest request
    ){
        BusinessWorkingHoursResponse businessResponse = businessWorkingHoursService
                .createBusinessWorkingHours(businessId, request);

        return new ResponseEntity<>(businessResponse, HttpStatus.CREATED);
    }

    @GetMapping("/businesses/{businessId}/working-hours")
    public ResponseEntity<List<BusinessWorkingHoursResponse>> getBusinessWorkingHours(
            @PathVariable Long businessId
    ){
        List<BusinessWorkingHoursResponse> businessResponse = businessWorkingHoursService
                .getBusinessWorkingHours(businessId);

        return ResponseEntity.ok(businessResponse);
    }


}
