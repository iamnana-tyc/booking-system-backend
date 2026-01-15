package com.iamnana.booking.dto;

import lombok.Data;

import java.util.List;

@Data
public class ServiceOfferingResponse {
    private List<ServiceOfferingResponseItem> serviceOfferings;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}
