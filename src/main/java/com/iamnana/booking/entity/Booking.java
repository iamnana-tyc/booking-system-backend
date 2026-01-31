package com.iamnana.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "uq_business_date_start_time",
                columnNames = {"business_id", "date", "start_time"} )
        }) //This is not to allow two bookings to start at the same time for the same business.
public class Booking extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "business_id",  nullable = false)
    private Business business;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "service_offering_id",  nullable = false)
    private ServiceOffering serviceOffering;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;
}
