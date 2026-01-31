package com.iamnana.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BusinessRequest {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Boolean active;
}
