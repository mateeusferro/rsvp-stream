package com.ferro.mateus.rsvpstream.controller.dto;

import java.time.LocalDate;

public record EventDTO(String title, String description, String location, String category, LocalDate date) {
}
