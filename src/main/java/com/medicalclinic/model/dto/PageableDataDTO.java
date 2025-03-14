package com.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
public class PageableDataDTO<T> {
    Set<T> data;
    private int totalPages;
    private int currentPage;
    private long totalElements;
}
