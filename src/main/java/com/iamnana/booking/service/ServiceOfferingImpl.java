package com.iamnana.booking.service;


import com.iamnana.booking.dto.ServiceOfferingDTO;
import com.iamnana.booking.dto.ServiceOfferingResponse;
import com.iamnana.booking.repository.ServiceOfferingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceOfferingImpl implements ServiceOffering  {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final ModelMapper modelMapper;

    @Override
    public ServiceOfferingDTO createServiceOffering(ServiceOfferingDTO serviceOfferingDTO) {
        return null;
    }

    @Override
    public ServiceOfferingDTO updateServiceOffering(ServiceOfferingDTO serviceOfferingDTO, Long id) {
        return null;
    }

    @Override
    public ServiceOfferingResponse getAllServiceOfferings() {
        return null;
    }

    @Override
    public ServiceOfferingDTO getServiceOfferingById(Long id) {
        return null;
    }

    @Override
    public void deleteServiceOffering(Long id) {

    }
}
