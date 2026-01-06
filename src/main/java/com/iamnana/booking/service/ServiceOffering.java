package com.iamnana.booking.service;

import com.iamnana.booking.dto.ServiceOfferingDTO;
import com.iamnana.booking.dto.ServiceOfferingResponse;

public interface ServiceOffering {
    ServiceOfferingDTO createServiceOffering(ServiceOfferingDTO serviceOfferingDTO);
    ServiceOfferingDTO updateServiceOffering(ServiceOfferingDTO serviceOfferingDTO, Long id);
    ServiceOfferingResponse getAllServiceOfferings();
    ServiceOfferingDTO getServiceOfferingById(Long id);
    void deleteServiceOffering(Long id);
}
