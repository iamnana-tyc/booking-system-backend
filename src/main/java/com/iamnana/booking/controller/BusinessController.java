package com.iamnana.booking.controller;

import com.iamnana.booking.config.AppConstant;
import com.iamnana.booking.dto.BusinessItemResponse;
import com.iamnana.booking.dto.BusinessPatchRequest;
import com.iamnana.booking.dto.BusinessRequest;
import com.iamnana.booking.dto.BusinessResponseDto;
import com.iamnana.booking.service.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BusinessController {
    private final BusinessService businessService;

    @PostMapping("/businesses")
    public ResponseEntity<BusinessItemResponse> createBusiness(
            @Valid @RequestBody BusinessRequest businessRequest) {
        BusinessItemResponse business = businessService.createBusiness(businessRequest);

        return new ResponseEntity<>(business, HttpStatus.CREATED);
    }

    @GetMapping("/businesses")
    public ResponseEntity<BusinessResponseDto> getAllBusinesses(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER,  required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE,  required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BUSINESS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIRECTION, required = false) String sortOrder
    ) {
        BusinessResponseDto business = businessService.getAllBusinesses(pageNumber,pageSize,sortBy,sortOrder);

        return new ResponseEntity<>(business, HttpStatus.OK);
    }

    @GetMapping("/businesses/search")
    public ResponseEntity<BusinessResponseDto> searchBusinesses(
            @RequestParam String search,
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER,  required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE,  required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BUSINESS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIRECTION, required = false) String sortOrder) {
        BusinessResponseDto business = businessService.searchBusinesses(search, pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(business, HttpStatus.OK);
    }

    @DeleteMapping("/businesses/{businessId}")
    public ResponseEntity<String> deleteBusiness(@PathVariable Long businessId) {
        businessService.deleteBusiness(businessId);

        return ResponseEntity.ok("Business deleted successfully");
    }

    @PatchMapping("/businesses/{businessId}")
    public ResponseEntity<BusinessItemResponse> updateBusiness(
            @RequestBody BusinessPatchRequest businessRequest,
            @PathVariable Long businessId
                                                               ) {
        BusinessItemResponse business = businessService.updateBusiness(businessRequest, businessId);

        return ResponseEntity.ok(business);
    }

    @PutMapping("/businesses/{businessId}")
    public ResponseEntity<BusinessItemResponse> replaceBusiness(
            @Valid @RequestBody BusinessRequest request,
            @PathVariable Long businessId) {

        return ResponseEntity.ok(
                businessService.replaceBusiness(request, businessId)
        );
    }
}
