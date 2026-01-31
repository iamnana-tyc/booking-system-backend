package com.iamnana.booking.service;

import com.iamnana.booking.dto.BookingRequest;
import com.iamnana.booking.dto.BookingResponse;
import com.iamnana.booking.entity.*;
import com.iamnana.booking.exception.APIException;
import com.iamnana.booking.exception.ResourceNotFoundException;
import com.iamnana.booking.repository.BookingRepository;
import com.iamnana.booking.repository.BusinessRepository;
import com.iamnana.booking.repository.BusinessWorkingHoursRepository;
import com.iamnana.booking.repository.ServiceOfferingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BusinessRepository businessRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final BusinessWorkingHoursRepository businessWorkingHoursRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public BookingResponse createBooking(Long businessId, Long serviceId, BookingRequest request) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(()-> new ResourceNotFoundException("Business", "businessId", businessId));

        ServiceOffering serviceOffering = serviceOfferingRepository
                .findByIdAndBusinessId(serviceId, businessId)
                .orElseThrow(()-> new ResourceNotFoundException("ServiceOffering", "serviceId", serviceId));

        DayOfWeek dayOfWeek = request.getDate().getDayOfWeek();

        // Find the business working hours for the business
        BusinessWorkingHours workingHours = businessWorkingHoursRepository
                .findByBusinessIdAndDayOfWeek(businessId, dayOfWeek)
                .orElseThrow(() -> new APIException("Business is closed on " + dayOfWeek));

        LocalTime startTime = request.getStartTime();
        LocalTime endtime = startTime.plusMinutes(serviceOffering.getDurationInMinutes());

        // Validate business hours
        if (startTime.isBefore(workingHours.getOpenTime()) || endtime.isAfter(workingHours.getCloseTime()))  {
            throw new APIException("Business time is  outside business working hours for " + dayOfWeek);
        }

        // handle overlapping bookings
        boolean hasOverlap = bookingRepository
                .existsByBusinessIdAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
                        businessId,
                        request.getDate(),
                        startTime,
                        endtime
                );

        if (hasOverlap) {
            throw new APIException("Selected time slot is already booked");
        }

        // Create booking
        Booking booking = new Booking();
        booking.setBusiness(business);
        booking.setServiceOffering(serviceOffering);
        booking.setDate(request.getDate());
        booking.setStartTime(startTime);
        booking.setEndTime(endtime);
        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);

        return modelMapper.map(savedBooking, BookingResponse.class);
    }

    @Override
    public List<BookingResponse> getAllBookingsForBusiness(Long businessId) {
        businessRepository.findById(businessId)
                .orElseThrow(()-> new ResourceNotFoundException("Business", "businessId", businessId));

        List<Booking> bookings = bookingRepository.findByBusinessId(businessId);

        return bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookingResponse confirmBooking(Long businessId, Long bookingId) {
        Booking booking = bookingRepository.findByIdAndBusinessId(bookingId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId", bookingId));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new APIException("Only pending bookings can be confirmed.");
        }

        boolean hasOverlap = bookingRepository
                .existsByBusinessIdAndDateAndStartTimeLessThanAndEndTimeGreaterThanAndStatus(
                        businessId,
                        booking.getDate(),
                        booking.getStartTime(),
                        booking.getEndTime(),
                        BookingStatus.CONFIRMED
                );
        if (hasOverlap) {
            throw new APIException("Selected time slot is already booked");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        Booking updatedBooking = bookingRepository.save(booking);

        return modelMapper.map(updatedBooking, BookingResponse.class);
    }
}
