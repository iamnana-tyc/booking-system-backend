package com.iamnana.booking.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceOfferingUpdateRequest {
    private String name;
    private String description;
    private Integer durationInMinutes;
    private BigDecimal price;
    private Boolean active;
}
