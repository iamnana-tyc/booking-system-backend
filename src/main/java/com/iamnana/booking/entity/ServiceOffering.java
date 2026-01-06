package com.iamnana.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "service_offerings")
public class ServiceOffering extends BaseEntity {

    @NotBlank(message = "Service name is required.")
    @Column(nullable = false)
    private String name;
    private String description;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than zero")
    @Column(nullable = false)
    private Integer durationInMinutes;

    @NotNull(message = "Price for service is required")
    @Positive(message = "Price must be greater than zero")
    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "business_id",  nullable = false)
    private Business business;
}
