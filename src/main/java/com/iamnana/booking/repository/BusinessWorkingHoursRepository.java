package com.iamnana.booking.repository;


import com.iamnana.booking.entity.Business;
import com.iamnana.booking.entity.BusinessWorkingHours;
import com.iamnana.booking.entity.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessWorkingHoursRepository extends JpaRepository<BusinessWorkingHours, Long> {

    Boolean existsByBusinessAndDayOfWeek(Business business, DayOfWeek dayOfWeek);
}
