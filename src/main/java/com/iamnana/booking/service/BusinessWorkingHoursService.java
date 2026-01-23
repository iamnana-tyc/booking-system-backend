package com.iamnana.booking.service;

import com.iamnana.booking.dto.BusinessWorkingHoursRequest;
import com.iamnana.booking.dto.BusinessWorkingHoursResponse;

import java.util.List;

public interface BusinessWorkingHoursService {
    BusinessWorkingHoursResponse createBusinessWorkingHours(Long businessId, BusinessWorkingHoursRequest request);

    List<BusinessWorkingHoursResponse> getBusinessWorkingHours(Long businessId);
}
