package com.iamnana.booking.controller;

import com.iamnana.booking.config.AppConstant;
import com.iamnana.booking.dto.BusinessRequest;
import com.iamnana.booking.dto.BusinessResponseDto;
import com.iamnana.booking.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/business")
    public ResponseEntity<BusinessResponseDto> getAllBusinesses(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER,  required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE,  required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BUSINESS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIRECTION, required = false) String sortOrder
    ) {
        BusinessResponseDto business = businessService.getAllBusinesses(pageNumber,pageSize,sortBy,sortOrder);

        return new ResponseEntity<>(business, HttpStatus.OK);
    }

    @GetMapping("/business/{search}")
    public ResponseEntity<BusinessResponseDto> searchBusinesses(
            @PathVariable String search,
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER,  required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE,  required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_BUSINESS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIRECTION, required = false) String sortOrder) {
        BusinessResponseDto business = businessService.searchBusinesses(search, pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(business, HttpStatus.OK);
    }

}
