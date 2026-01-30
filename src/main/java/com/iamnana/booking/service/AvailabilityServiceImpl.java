package com.iamnana.booking.service;

import com.iamnana.booking.dto.AvailabilityResponse;
import com.iamnana.booking.dto.AvailabilitySlotResponse;
import com.iamnana.booking.entity.BusinessWorkingHours;
import com.iamnana.booking.entity.ServiceOffering;
import com.iamnana.booking.exception.APIException;
import com.iamnana.booking.exception.ResourceNotFoundException;
import com.iamnana.booking.repository.BusinessRepository;
import com.iamnana.booking.repository.BusinessWorkingHoursRepository;
import com.iamnana.booking.repository.ServiceOfferingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {
    private final BusinessRepository businessRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final BusinessWorkingHoursRepository businessWorkingHoursRepository;

    @Override
    public AvailabilityResponse getAvailabilityTime(Long businessId,  Long serviceId, LocalDate date) {
        businessRepository.findById(businessId)
                .orElseThrow(()-> new ResourceNotFoundException("Business", "businessId", businessId));

        ServiceOffering serviceOffering = serviceOfferingRepository.findByIdAndBusinessId(serviceId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("ServiceOffering", "serviceId", serviceId));

        // convert date to day of week
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // Find the business working hours for the business
        BusinessWorkingHours workingHours = businessWorkingHoursRepository.findByBusinessIdAndDayOfWeek(businessId, dayOfWeek)
                        .orElseThrow(() -> new APIException("Business is closed on " + dayOfWeek));

        LocalTime openTime = workingHours.getOpenTime();
        LocalTime closeTime = workingHours.getCloseTime();

        int durationMinutes = serviceOffering.getDurationInMinutes();

        List<AvailabilitySlotResponse> slots = new ArrayList<>();
        LocalTime slotStart = openTime;

        while (true) {
            LocalTime slotEnd = slotStart.plusMinutes(durationMinutes);

            // Stop if slot exceeds business hours
            if (slotEnd.isAfter(closeTime)) {
                break;
            }

            AvailabilitySlotResponse slot = new AvailabilitySlotResponse();
            slot.setStartTime(slotStart);
            slot.setEndTime(slotEnd);

            slots.add(slot);

            // Move to next slot
            slotStart = slotEnd;
        }

        AvailabilityResponse response = new AvailabilityResponse();
        response.setDate(date);
        response.setSlots(slots);

        return response;
    }
}
