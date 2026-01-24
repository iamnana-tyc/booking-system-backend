package com.iamnana.booking.repository;


import com.iamnana.booking.entity.Business;
import com.iamnana.booking.entity.BusinessWorkingHours;
import com.iamnana.booking.entity.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessWorkingHoursRepository extends JpaRepository<BusinessWorkingHours, Long> {

    Boolean existsByBusinessAndDayOfWeek(Business business, DayOfWeek dayOfWeek);

    List<BusinessWorkingHours> findByBusinessId(Long businessId);

    Optional<BusinessWorkingHours> findByIdAndBusinessId(Long workingHoursId, Long businessId);

    boolean existsByBusinessIdAndDayOfWeek(Long businessId, DayOfWeek dayOfWeek);
}
