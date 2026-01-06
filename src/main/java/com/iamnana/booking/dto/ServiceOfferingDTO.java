package com.iamnana.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOfferingDTO {
    private String name;
    private String description;
    private Integer durationInMinutes;
    private BigDecimal price;
    private Boolean active;
}
