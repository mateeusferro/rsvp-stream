package com.ferro.mateus.rsvpstream.controller.dto;

import org.springframework.data.domain.Page;

public record PaginationResponse(Integer page, Integer pageSize,
                                 Long total, Integer totalPages) {
    public static PaginationResponse fromPage(Page<?> page) {
        return new PaginationResponse(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
