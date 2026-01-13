package com.iamnana.booking.controller;

import com.iamnana.booking.config.AppConstant;
import com.iamnana.booking.dto.ServiceOfferingCreateRequest;
import com.iamnana.booking.dto.ServiceOfferingResponse;
import com.iamnana.booking.dto.ServiceOfferingResponseItem;
import com.iamnana.booking.dto.ServiceOfferingUpdateRequest;
import com.iamnana.booking.service.ServiceOfferingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ServiceOfferingController {
    private final ServiceOfferingService serviceOfferingService;

    @PostMapping("/businesses/{businessId}/services")
    public ResponseEntity<ServiceOfferingResponseItem> createServiceOffering(
            @Valid @RequestBody ServiceOfferingCreateRequest request,
            @PathVariable Long businessId
    ){
        ServiceOfferingResponseItem createService = serviceOfferingService.createServiceOffering(request, businessId);

        return new ResponseEntity<>(createService, HttpStatus.CREATED);
    }

    @GetMapping("/public/businesses/{businessId}/services")
    public ResponseEntity<ServiceOfferingResponse> getAllServiceOfferingsForBusiness(
            @PathVariable Long businessId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER,  required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE,  required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_SERVICE_OFFERING_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIRECTION, required = false) String sortOrder
    ){
        ServiceOfferingResponse response = serviceOfferingService.getAllServiceOfferingsForBusiness(
                businessId,
                pageNumber,
                pageSize,
                sortBy,
                sortOrder
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/businesses/{business-id}/service/{service-id}")
    public ResponseEntity<ServiceOfferingUpdateRequest> updateServiceOffering(
            @RequestBody ServiceOfferingUpdateRequest request,
            @PathVariable("business-id") Long businessId,
            @PathVariable("service-id") Long serviceId
    ){
        ServiceOfferingUpdateRequest createService = serviceOfferingService.updateServiceOffering(request, businessId, serviceId);

        return new ResponseEntity<>(createService, HttpStatus.OK);
    }

    @DeleteMapping("admin/services/{service-id}")
    public ResponseEntity<String> deleteServiceOffering(@PathVariable("service-id") Long serviceId){
        serviceOfferingService.deleteServiceOffering(serviceId);

        return new ResponseEntity<>("Service offering successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/services/{search}")
    public ResponseEntity<ServiceOfferingResponse> searchServiceOfferingByKeyword(
            @PathVariable String search,
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER,  required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE,  required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_SERVICE_OFFERING_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIRECTION, required = false) String sortOrder
    ){
        ServiceOfferingResponse response = serviceOfferingService.searchServiceOfferingByKeyword(
                search,
                pageNumber,
                pageSize,
                sortBy,
                sortOrder
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
