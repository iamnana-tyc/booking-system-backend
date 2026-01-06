package com.iamnana.booking.dto;

import lombok.Data;

import java.util.List;

@Data
public class ServiceOfferingResponse {
    private List<ServiceOfferingDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Integer totalNumberElements;
    private Boolean isLastPage;
}
