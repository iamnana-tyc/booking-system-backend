package com.iamnana.booking.repository;

import com.iamnana.booking.entity.ServiceOffering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {
    @Query("SELECT s FROM ServiceOffering s WHERE s.name = ?1 AND s.business.id = ?2")
    ServiceOffering findByNameAndBusinessId(String name, Long businessId);

    Page<ServiceOffering> findByBusinessId(Long businessId, Pageable pageable);

    Page<ServiceOffering> findByNameContainingIgnoreCase(String search, Pageable pageDetails);
}
