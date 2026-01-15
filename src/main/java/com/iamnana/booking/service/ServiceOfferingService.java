package com.iamnana.booking.service;

import com.iamnana.booking.dto.ServiceOfferingPatchRequest;
import com.iamnana.booking.dto.ServiceOfferingRequest;
import com.iamnana.booking.dto.ServiceOfferingResponse;
import com.iamnana.booking.dto.ServiceOfferingResponseItem;


public interface ServiceOfferingService {
    ServiceOfferingResponseItem createServiceOffering(ServiceOfferingRequest request, Long businessId);
    ServiceOfferingResponse getAllServiceOfferingsForBusiness(Long businessId, int pageNumber, int pageSize, String sortBy, String sortDir);
    ServiceOfferingResponseItem updateServiceOffering(ServiceOfferingRequest request, Long businessId, Long serviceId);
    void deleteServiceOffering(Long serviceId);
    ServiceOfferingResponse searchServiceOfferingByKeyword(String search, int pageNumber, int pageSize, String sortBy, String sortOrder);
    ServiceOfferingResponseItem getServiceOfferingById(Long serviceId);
    ServiceOfferingResponseItem updateServiceOfferingById(Long serviceId, Long businessId, ServiceOfferingPatchRequest request);
}
