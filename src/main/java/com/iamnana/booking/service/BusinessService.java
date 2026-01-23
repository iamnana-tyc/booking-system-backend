package com.iamnana.booking.service;

import com.iamnana.booking.dto.BusinessItemResponse;
import com.iamnana.booking.dto.BusinessPatchRequest;
import com.iamnana.booking.dto.BusinessRequest;
import com.iamnana.booking.dto.BusinessResponseDto;


public interface BusinessService {
    BusinessItemResponse createBusiness(BusinessRequest businessRequest);
    BusinessResponseDto getAllBusinesses(int pageNumber, int pageSize, String sortBy, String sortOrder);
    BusinessResponseDto searchBusinesses(String search, int pageNumber, int pageSize, String sortBy, String sortOrder);
    void deleteBusiness(Long businessId);
    BusinessItemResponse partialUpdateBusiness(BusinessPatchRequest businessRequest, Long businessId);
    BusinessItemResponse updateBusiness(BusinessRequest request, Long businessId);
}
