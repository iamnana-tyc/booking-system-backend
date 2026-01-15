package com.iamnana.booking.repository;

import com.iamnana.booking.entity.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Business findBusinessByName(String name);

    Page<Business> findByNameContainingIgnoreCase(String search, Pageable pageable);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long businessId);
}
