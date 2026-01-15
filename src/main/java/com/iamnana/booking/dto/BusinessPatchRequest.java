package com.iamnana.booking.dto;

import lombok.Data;

@Data
public class BusinessPatchRequest {
    private String name;
    private String description;
    private Boolean active;
}
