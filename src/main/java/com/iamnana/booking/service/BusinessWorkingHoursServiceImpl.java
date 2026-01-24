package com.iamnana.booking.service;

import com.iamnana.booking.dto.BusinessWorkingHoursPatchRequest;
import com.iamnana.booking.dto.BusinessWorkingHoursRequest;
import com.iamnana.booking.dto.BusinessWorkingHoursResponse;
import com.iamnana.booking.entity.Business;
import com.iamnana.booking.entity.BusinessWorkingHours;
import com.iamnana.booking.exception.APIException;
import com.iamnana.booking.exception.ResourceNotFoundException;
import com.iamnana.booking.repository.BusinessRepository;
import com.iamnana.booking.repository.BusinessWorkingHoursRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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
        boolean existWorkingHours = businessWorkingHoursRepository.existsByBusinessAndDayOfWeek(business, request.getDayOfWeek());
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

        List<BusinessWorkingHours> workingHours = businessWorkingHoursRepository.findByBusinessId(businessId);

        return workingHours.stream()
                .map(workingHour -> modelMapper.map(workingHour, BusinessWorkingHoursResponse.class))
                .toList();
    }

    @Transactional
    @Override
    public BusinessWorkingHoursResponse partialUpdateBusinessWorkingHours(Long businessId, BusinessWorkingHoursPatchRequest request, Long workingHoursId) {
        businessRepository.findById(businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Business", "businessId", businessId));

        BusinessWorkingHours existingWorkHours = businessWorkingHoursRepository
                .findByIdAndBusinessId(workingHoursId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "BusinessWorkingHours", "workingHoursId", workingHoursId));

        if (request.getDayOfWeek() != null &&
                !request.getDayOfWeek().equals(existingWorkHours.getDayOfWeek()) &&
                businessWorkingHoursRepository.existsByBusinessIdAndDayOfWeek(businessId, request.getDayOfWeek())) {
            throw new APIException("Working hours already exist for this business on " + request.getDayOfWeek());
        }

        LocalTime openTime = request.getOpenTime() != null
                ? request.getOpenTime()
                : existingWorkHours.getOpenTime();

        LocalTime closeTime = request.getCloseTime() != null
                ? request.getCloseTime()
                : existingWorkHours.getCloseTime();

        if (!openTime.isBefore(closeTime)) {
            throw new APIException("Open time must be before close time");
        }

        if (request.getDayOfWeek() != null)
            existingWorkHours.setDayOfWeek(request.getDayOfWeek());
        if (request.getOpenTime() != null)
            existingWorkHours.setOpenTime(request.getOpenTime());
        if (request.getCloseTime() != null)
            existingWorkHours.setCloseTime(request.getCloseTime());

        BusinessWorkingHours saveUpdatedBusinessWorkingHours =
                businessWorkingHoursRepository.save(existingWorkHours);

        return modelMapper.map(saveUpdatedBusinessWorkingHours, BusinessWorkingHoursResponse.class);
    }

    @Transactional
    @Override
    public void deleteBusinessWorkingHours(Long businessId, Long workingHoursId) {
        BusinessWorkingHours workingHours =
                businessWorkingHoursRepository.findByIdAndBusinessId(workingHoursId, businessId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "BusinessWorkingHours", "workingHoursId", workingHoursId));

        businessWorkingHoursRepository.delete(workingHours);
    }
}
