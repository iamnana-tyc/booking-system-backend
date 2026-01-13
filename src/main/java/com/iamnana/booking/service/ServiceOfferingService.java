package com.iamnana.booking.service;

import com.iamnana.booking.dto.ServiceOfferingCreateRequest;
import com.iamnana.booking.dto.ServiceOfferingResponse;
import com.iamnana.booking.dto.ServiceOfferingResponseItem;
import com.iamnana.booking.dto.ServiceOfferingUpdateRequest;


public interface ServiceOfferingService {
    ServiceOfferingResponseItem createServiceOffering(ServiceOfferingCreateRequest request, Long businessId);
    ServiceOfferingResponse getAllServiceOfferingsForBusiness(Long businessId, int pageNumber, int pageSize, String sortBy, String sortDir);
    ServiceOfferingUpdateRequest updateServiceOffering(ServiceOfferingUpdateRequest request, Long businessId, Long serviceId);
    void deleteServiceOffering(Long serviceId);
    ServiceOfferingResponse searchServiceOfferingByKeyword(String search, int pageNumber, int pageSize, String sortBy, String sortOrder);
}
