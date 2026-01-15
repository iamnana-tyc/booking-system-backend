package com.iamnana.booking.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceOfferingPatchRequest {
    @Size(min = 1, message = "Name must not be empty")
    private String name;

    @Size(min = 1, message = "Description must not be empty")
    private String description;

    @Positive(message = "Duration must be greater than zero")
    private Integer durationInMinutes;

    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    private Boolean active;
}
