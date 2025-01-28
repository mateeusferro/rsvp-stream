package com.ferro.mateus.rsvpstream.service;

import com.ferro.mateus.rsvpstream.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EventService {
    Event saveEvent(Event event);
    Event getEventById(UUID id);
    Page<Event> getAllEvents(Pageable pageable);
}
