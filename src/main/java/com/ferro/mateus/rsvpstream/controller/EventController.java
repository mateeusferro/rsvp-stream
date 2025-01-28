package com.ferro.mateus.rsvpstream.controller;

import com.ferro.mateus.rsvpstream.controller.dto.APIResponse;
import com.ferro.mateus.rsvpstream.controller.dto.EventDTO;
import com.ferro.mateus.rsvpstream.controller.dto.PaginationResponse;
import com.ferro.mateus.rsvpstream.domain.Event;
import com.ferro.mateus.rsvpstream.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1/event")
public class EventController {

    private final EventService eventService;
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO eventDTO) {
        Event event = Event.builder()
                .title(eventDTO.title())
                .description(eventDTO.description())
                .category(eventDTO.category())
                .location(eventDTO.location())
                .date(eventDTO.date())
                .build();
        Event savedEvent = eventService.saveEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    @GetMapping
    public ResponseEntity<APIResponse<Event>> findAllEvents(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        Page<Event> events = eventService.getAllEvents(PageRequest.of(page, size));
        return new ResponseEntity<>(
                new APIResponse<>(events.getContent(),
                        PaginationResponse.fromPage(events)
                ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> find(@PathVariable UUID id) {
        return new ResponseEntity<>(eventService.getEventById(id), HttpStatus.OK);
    }
}
