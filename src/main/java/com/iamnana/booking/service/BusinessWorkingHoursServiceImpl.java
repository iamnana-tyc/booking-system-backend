package com.iamnana.booking.service;

import com.iamnana.booking.dto.BusinessWorkingHoursRequest;
import com.iamnana.booking.dto.BusinessWorkingHoursResponse;
import com.iamnana.booking.entity.Business;
import com.iamnana.booking.entity.BusinessWorkingHours;
import com.iamnana.booking.exception.APIException;
import com.iamnana.booking.exception.ResourceNotFoundException;
import com.iamnana.booking.repository.BusinessRepository;
import com.iamnana.booking.repository.BusinessWorkingHoursRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessWorkingHoursServiceImpl implements BusinessWorkingHoursService {
    private final ModelMapper modelMapper;
    private final BusinessWorkingHoursRepository businessWorkingHoursRepository;
    private final BusinessRepository businessRepository;

    @Override
    public BusinessWorkingHoursResponse createBusinessWorkingHours(Long businessId, BusinessWorkingHoursRequest request) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Business", "businessId", businessId));

        // Prevent duplicate working hours for the same business and day
        Boolean existWorkingHours = businessWorkingHoursRepository.existsByBusinessAndDayOfWeek(business, request.getDayOfWeek());
        if (existWorkingHours) {
            throw new APIException("Business working hours already exists for " + request.getDayOfWeek() + " for this business" );
        }

        // Validate time range
        if (!request.getOpenTime().isBefore(request.getCloseTime())){
            throw new APIException("Open time must be before close time");
        }

        BusinessWorkingHours businessWorkingHours = modelMapper.map(request, BusinessWorkingHours.class);
        businessWorkingHours.setBusiness(business);

        BusinessWorkingHours savedWorkingHours = businessWorkingHoursRepository.save(businessWorkingHours);

        return modelMapper.map(savedWorkingHours, BusinessWorkingHoursResponse.class);

    }

    @Override
    public List<BusinessWorkingHoursResponse> getBusinessWorkingHours(Long businessId) {
        businessRepository.findById(businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Business", "businessId", businessId));

        List<BusinessWorkingHours> workingHours = businessWorkingHoursRepository.findAll();

        return workingHours.stream()
                .map(workingHour -> modelMapper.map(workingHour, BusinessWorkingHoursResponse.class))
                .toList();
    }
}
