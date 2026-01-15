package com.iamnana.booking.service;

import com.iamnana.booking.dto.BusinessItemResponse;
import com.iamnana.booking.dto.BusinessPatchRequest;
import com.iamnana.booking.dto.BusinessRequest;
import com.iamnana.booking.dto.BusinessResponseDto;
import com.iamnana.booking.entity.Business;
import com.iamnana.booking.exception.APIException;
import com.iamnana.booking.exception.ResourceNotFoundException;
import com.iamnana.booking.repository.BusinessRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {
    private final BusinessRepository businessRepository;
    private final ModelMapper modelMapper;

    @Override
    public BusinessItemResponse createBusiness(BusinessRequest businessRequest) {
        if (businessRepository.existsByName(businessRequest.getName())){
            throw new APIException("Business already exists");
        }

        Business business = modelMapper.map(businessRequest, Business.class);
        Business savedBusiness = businessRepository.save(business);

        return modelMapper.map(savedBusiness, BusinessItemResponse.class);
    }

    @Override
    public BusinessResponseDto getAllBusinesses(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sortAndOrderBy = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortAndOrderBy);
        Page<Business> businessPage = businessRepository.findAll(pageable);
        List<Business> businesses = businessPage.getContent();

        List<BusinessItemResponse> businessRequests = businesses.stream()
                .map(business -> modelMapper.map(business, BusinessItemResponse.class))
                .toList();

        BusinessResponseDto response = new BusinessResponseDto();
        response.setBusinesses(businessRequests);
        response.setPageNumber(businessPage.getNumber());
        response.setPageSize(businessPage.getSize());
        response.setTotalElements(businessPage.getTotalElements());
        response.setTotalPages(businessPage.getTotalPages());
        response.setLastPage(businessPage.isLast());

        return response;
    }

    @Override
    public BusinessResponseDto searchBusinesses(String search, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sortAndOrderBy = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortAndOrderBy);
        Page<Business> businessPage = businessRepository.findByNameContainingIgnoreCase(search, pageable);
        List<Business> businesses = businessPage.getContent();

        List<BusinessItemResponse> businessRequests = businesses.stream()
                .map(business -> modelMapper.map(business, BusinessItemResponse.class))
                .toList();
        BusinessResponseDto response = new BusinessResponseDto();
        response.setBusinesses(businessRequests);
        response.setPageNumber(businessPage.getNumber());
        response.setPageSize(businessPage.getSize());
        response.setTotalPages(businessPage.getTotalPages());
        response.setTotalElements(businessPage.getTotalElements());
        response.setLastPage(businessPage.isLast());

        return response;
    }

    @Transactional
    @Override
    public void deleteBusiness(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(()-> new ResourceNotFoundException("Business", "businessId", businessId));
        businessRepository.delete(business);
    }

    @Transactional
    @Override
    public BusinessItemResponse updateBusiness(BusinessPatchRequest businessRequest, Long businessId) {
        Business existingBusiness = businessRepository.findById(businessId)
                .orElseThrow(()-> new ResourceNotFoundException("Business", "businessId", businessId));

        if (businessRequest.getName() != null &&
                !businessRequest.getName().equals(existingBusiness.getName()) &&
                businessRepository.existsByNameAndIdNot(
                        businessRequest.getName(), businessId)) {

            throw new APIException("Business name already exists");
        }

        if (businessRequest.getName() != null) {
            existingBusiness.setName(businessRequest.getName());
        }

        if (businessRequest.getDescription() != null) {
            existingBusiness.setDescription(businessRequest.getDescription());
        }

        if (businessRequest.getActive() != null) {
            existingBusiness.setActive(businessRequest.getActive());
        }

        Business updatedBusiness = businessRepository.save(existingBusiness);

        return modelMapper.map(updatedBusiness, BusinessItemResponse.class);
    }

    @Transactional
    @Override
    public BusinessItemResponse replaceBusiness(BusinessRequest request, Long businessId) {
        Business exitingBusiness = businessRepository.findById(businessId)
                .orElseThrow(()-> new ResourceNotFoundException("Business", "businessId", businessId));

        exitingBusiness.setName(request.getName());
        exitingBusiness.setDescription(request.getDescription());
        exitingBusiness.setActive(request.getActive());

        Business updatedBusiness = businessRepository.save(exitingBusiness);

        return modelMapper.map(updatedBusiness, BusinessItemResponse.class);
    }
}
