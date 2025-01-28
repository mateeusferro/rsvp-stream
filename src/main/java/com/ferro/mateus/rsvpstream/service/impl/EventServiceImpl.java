package com.ferro.mateus.rsvpstream.service.impl;

import com.ferro.mateus.rsvpstream.domain.Event;
import com.ferro.mateus.rsvpstream.repository.EventRepository;
import com.ferro.mateus.rsvpstream.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event getEventById(UUID id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }
}
