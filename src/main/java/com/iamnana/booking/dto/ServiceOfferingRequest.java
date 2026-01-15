package com.iamnana.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceOfferingRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Integer durationInMinutes;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private Boolean active;
}
