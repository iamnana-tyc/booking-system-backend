package com.iamnana.booking.dto;

import lombok.Data;

import java.util.List;

@Data
public class BusinessResponseDto {
    private List<BusinessItemResponse> businesses;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}
