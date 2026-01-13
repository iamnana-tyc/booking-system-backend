package com.iamnana.booking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BusinessRequest {
    private Long businessId;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private Boolean active;
}
