package com.iamnana.booking.service;


import com.iamnana.booking.dto.ServiceOfferingPatchRequest;
import com.iamnana.booking.dto.ServiceOfferingRequest;
import com.iamnana.booking.dto.ServiceOfferingResponse;
import com.iamnana.booking.dto.ServiceOfferingResponseItem;
import com.iamnana.booking.entity.Business;
import com.iamnana.booking.entity.ServiceOffering;
import com.iamnana.booking.exception.APIException;
import com.iamnana.booking.exception.ResourceNotFoundException;
import com.iamnana.booking.repository.BusinessRepository;
import com.iamnana.booking.repository.ServiceOfferingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final ModelMapper modelMapper;
    private final BusinessRepository businessRepository;

    @Override
    public ServiceOfferingResponseItem createServiceOffering(ServiceOfferingRequest request, Long businessId) {
        ServiceOffering service = serviceOfferingRepository.findByNameAndBusinessId(request.getName(), businessId);

        if (service != null) {
            throw new APIException("The Service already exists for this Business");
        }

        Business business = businessRepository.findById(businessId)
                .orElseThrow(()-> new ResourceNotFoundException("Business", "businessId", businessId));

        ServiceOffering serviceOffering = modelMapper.map(request, ServiceOffering.class);
        serviceOffering.setBusiness(business);

        try{
            ServiceOffering savedServiceOffering = serviceOfferingRepository.save(serviceOffering);

            return modelMapper.map(savedServiceOffering,  ServiceOfferingResponseItem.class);

        }catch(DataIntegrityViolationException exception){
            throw new APIException("Service with this name already exists for this business");
        }
    }


    @Override
    public ServiceOfferingResponse getAllServiceOfferingsForBusiness(Long businessId, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        businessRepository.findById(businessId)
                .orElseThrow(()-> new ResourceNotFoundException("Business", "businessId", businessId));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<ServiceOffering> serviceOfferingPage = serviceOfferingRepository.findByBusinessId(businessId, pageable);
        List<ServiceOffering> serviceOfferings = serviceOfferingPage.getContent();

        List<ServiceOfferingResponseItem> serviceOfferingDTO = serviceOfferings.stream()
                .map(serviceOffering -> modelMapper.map(serviceOffering, ServiceOfferingResponseItem.class))
                .toList();

        ServiceOfferingResponse response = new ServiceOfferingResponse();
        response.setServiceOfferings(serviceOfferingDTO);
        response.setPageNumber(serviceOfferingPage.getNumber());
        response.setPageSize(serviceOfferingPage.getSize());
        response.setTotalPages(serviceOfferingPage.getTotalPages());
        response.setTotalElements(serviceOfferingPage.getTotalElements());
        response.setLastPage(serviceOfferingPage.isLast());

        return response;
    }

    @Transactional
    @Override
    public ServiceOfferingResponseItem updateServiceOffering(ServiceOfferingRequest request, Long businessId, Long serviceId) {
        // Ensure that service belongs to this business.
        ServiceOffering existingServiceOffering = serviceOfferingRepository.findByIdAndBusinessId(serviceId, businessId)
                .orElseThrow(()-> new ResourceNotFoundException("ServiceOffering", "serviceId", serviceId));

        existingServiceOffering.setName(request.getName());
        existingServiceOffering.setDescription(request.getDescription());
        existingServiceOffering.setDurationInMinutes(request.getDurationInMinutes());
        existingServiceOffering.setPrice(request.getPrice());
        existingServiceOffering.setActive(request.getActive());

        ServiceOffering savedServiceOffering = serviceOfferingRepository.save(existingServiceOffering);

        return modelMapper.map(savedServiceOffering,  ServiceOfferingResponseItem.class);
    }

    @Transactional
    @Override
    public void deleteServiceOffering(Long serviceId) {
        ServiceOffering existingServiceOffering = serviceOfferingRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceOffering", "serviceId", serviceId));

        serviceOfferingRepository.delete(existingServiceOffering);
    }

    @Override
    public ServiceOfferingResponse searchServiceOfferingByKeyword(String search, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<ServiceOffering> page = serviceOfferingRepository.findByNameContainingIgnoreCase(search, pageDetails);
        List<ServiceOffering> serviceOfferings = page.getContent();

        List<ServiceOfferingResponseItem> serviceOfferingDTO = serviceOfferings.stream()
                .map(serviceOffering -> modelMapper.map(serviceOffering, ServiceOfferingResponseItem.class))
                .toList();

        if (serviceOfferingDTO.isEmpty()) {
            throw new  APIException("The service offering is not found" + search);
        }

        ServiceOfferingResponse response = new ServiceOfferingResponse();
        response.setServiceOfferings(serviceOfferingDTO);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());

        return response;
    }

    @Override
    public ServiceOfferingResponseItem getServiceOfferingById(Long serviceId) {
        ServiceOffering service = serviceOfferingRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceOffering", "serviceId", serviceId));

        return modelMapper.map(service, ServiceOfferingResponseItem.class);
    }

    @Transactional
    @Override
    public ServiceOfferingResponseItem updateServiceOfferingById(Long serviceId, Long businessId,  ServiceOfferingPatchRequest request) {
        ServiceOffering existingServiceOffering = serviceOfferingRepository.findByIdAndBusinessId(serviceId, businessId)
                .orElseThrow(()-> new ResourceNotFoundException("ServiceOffering", "serviceId", serviceId));

        if (request.getName() != null &&
                !request.getName().equals(existingServiceOffering.getName()) &&
                serviceOfferingRepository.existsByNameAndBusinessId(
                        request.getName(), businessId)) {

            throw new APIException("Service name already exists for this business");
        }

        if (request.getName() != null){
            existingServiceOffering.setName(request.getName());
        }

        if (request.getDescription() != null) {
            existingServiceOffering.setDescription(request.getDescription());
        }

        if (request.getDurationInMinutes() != null) {
            existingServiceOffering.setDurationInMinutes(request.getDurationInMinutes());
        }

        if (request.getPrice() != null) {
            existingServiceOffering.setPrice(request.getPrice());
        }

        if (request.getActive() != null) {
            existingServiceOffering.setActive(request.getActive());
        }

        ServiceOffering savedServiceOffering = serviceOfferingRepository.save(existingServiceOffering);

        return modelMapper.map(savedServiceOffering,  ServiceOfferingResponseItem.class);
    }
}
