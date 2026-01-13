package com.iamnana.booking.controller;

import com.iamnana.booking.config.AppConstant;
import com.iamnana.booking.dto.ServiceOfferingPatchRequest;
import com.iamnana.booking.dto.ServiceOfferingRequest;
import com.iamnana.booking.dto.ServiceOfferingResponse;
import com.iamnana.booking.dto.ServiceOfferingResponseItem;
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
            @Valid @RequestBody ServiceOfferingRequest request,
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

    @PutMapping("/businesses/{businessId}/services/{serviceId}")
    public ResponseEntity<ServiceOfferingResponseItem> updateServiceOffering(
            @Valid @RequestBody ServiceOfferingRequest request,
            @PathVariable Long businessId,
            @PathVariable Long serviceId
    ){
        ServiceOfferingResponseItem updated = serviceOfferingService.updateServiceOffering(request, businessId, serviceId);

        return ResponseEntity.ok(updated);
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

    @GetMapping("/services/{serviceId}")
    public ResponseEntity<ServiceOfferingResponseItem> getServiceOfferingById(@PathVariable Long serviceId){
        ServiceOfferingResponseItem serviceOffering = serviceOfferingService.getServiceOfferingById(serviceId);

        return new ResponseEntity<>(serviceOffering, HttpStatus.OK);
    }

    @PatchMapping("/businesses/{businessId}/services/{serviceId}")
    public ResponseEntity<ServiceOfferingResponseItem> updateServiceOfferingById(
            @PathVariable Long serviceId,
            @PathVariable Long businessId,
            @RequestBody ServiceOfferingPatchRequest request){
        ServiceOfferingResponseItem partialUpdate = serviceOfferingService
                .updateServiceOfferingById(serviceId,businessId, request);

        return ResponseEntity.ok(partialUpdate);
    }


}
