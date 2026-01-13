package com.iamnana.booking.dto;


import lombok.Data;

@Data
public class BusinessItemResponse {
    private String name;
    private String description;
    private Boolean active;
}
