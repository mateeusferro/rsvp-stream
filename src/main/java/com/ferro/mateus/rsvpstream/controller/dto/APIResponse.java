package com.ferro.mateus.rsvpstream.controller.dto;

import java.util.List;

public record APIResponse<T>(List<T> results, PaginationResponse paging) {
}