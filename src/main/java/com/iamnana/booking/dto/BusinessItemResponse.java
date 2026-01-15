package com.iamnana.booking.dto;


import lombok.Data;

@Data
public class BusinessItemResponse {
    private Long id;
    private String name;
    private String description;
    private Boolean active;
}
