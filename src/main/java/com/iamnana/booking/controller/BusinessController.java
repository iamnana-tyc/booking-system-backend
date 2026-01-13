package com.iamnana.booking.controller;

import com.iamnana.booking.dto.BusinessRequest;
import com.iamnana.booking.dto.BusinessResponse;
import com.iamnana.booking.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BusinessController {
    private final BusinessService businessService;

    @PostMapping("admin/business")
    public ResponseEntity<BusinessRequest> createBusiness(@RequestBody BusinessRequest businessRequest) {
        BusinessRequest business = businessService.createBusiness(businessRequest);

        return new ResponseEntity<>(business, HttpStatus.CREATED);
    }

    @GetMapping("public/business")
    public ResponseEntity<List<BusinessResponse>> getAllBusinesses() {
        List<BusinessResponse> business = businessService.getAllBusinesses();

        return new ResponseEntity<>(business, HttpStatus.OK);
    }


}
