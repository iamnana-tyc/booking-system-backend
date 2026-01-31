package com.iamnana.booking.repository;

import com.iamnana.booking.entity.Booking;
import com.iamnana.booking.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByBusinessIdAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Long businessId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    );

    List<Booking> findByBusinessId(Long businessId);

    Optional<Booking> findByIdAndBusinessId(Long bookingId, Long businessId);

    boolean existsByBusinessIdAndDateAndStartTimeLessThanAndEndTimeGreaterThanAndStatus(
            Long businessId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            BookingStatus bookingStatus
        );
}
