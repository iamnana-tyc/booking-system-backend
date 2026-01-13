package com.iamnana.booking.service;

import com.iamnana.booking.dto.BusinessItemResponse;
import com.iamnana.booking.dto.BusinessRequest;
import com.iamnana.booking.dto.BusinessResponseDto;
import com.iamnana.booking.entity.Business;
import com.iamnana.booking.exception.APIException;
import com.iamnana.booking.repository.BusinessRepository;
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
        Business existBusiness = businessRepository.findBusinessByName(businessRequest.getName());
        if (existBusiness != null){
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

        List<BusinessRequest> businessRequests = businesses.stream()
                .map(business -> modelMapper.map(business, BusinessRequest.class))
                .toList();

        BusinessResponseDto response = new BusinessResponseDto();
        response.setBusinessRequests(businessRequests);
        response.setPageNumber(pageNumber);
        response.setPageSize(pageSize);
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

        List<BusinessRequest> businessRequests = businesses.stream()
                .map(business -> modelMapper.map(business, BusinessRequest.class))
                .toList();
        BusinessResponseDto response = new BusinessResponseDto();
        response.setBusinessRequests(businessRequests);
        response.setPageNumber(businessPage.getNumber());
        response.setPageSize(businessPage.getSize());
        response.setTotalPages(businessPage.getTotalPages());
        response.setTotalElements(businessPage.getTotalElements());
        response.setLastPage(businessPage.isLast());

        return response;
    }

}
