package com.iamnana.booking.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceOfferingResponseItem {
    private Long id;
    private String name;
    private String description;
    private Integer durationInMinutes;
    private BigDecimal price;
    private Boolean active;
}
