package com.ferro.mateus.rsvpstream.service.impl;

import com.ferro.mateus.rsvpstream.domain.Event;
import com.ferro.mateus.rsvpstream.domain.Rsvp;
import com.ferro.mateus.rsvpstream.exception.EventNotFound;
import com.ferro.mateus.rsvpstream.repository.EventRepository;
import com.ferro.mateus.rsvpstream.repository.RsvpRepository;
import com.ferro.mateus.rsvpstream.service.RsvpService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RsvpServiceImpl implements RsvpService {

    private final RsvpRepository rsvpRepository;
    private final EventRepository eventRepository;

    public RsvpServiceImpl(RsvpRepository rsvpRepository, EventRepository eventRepository) {
        this.rsvpRepository = rsvpRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void processRsvp(UUID eventUuid, Rsvp rsvp) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Event event = eventRepository.findById(eventUuid).orElse(null);
                if (event == null) {
                    throw new EventNotFound("Event id doesn't exists: " + eventUuid);
                }
                rsvp.setEvent(event);
                rsvpRepository.save(rsvp);
                System.out.println("Processed RSVP for event: " + eventUuid + " by " + rsvp.getAttendeeName());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread was interrupted: " + e.getMessage());
            }
        }).start();
    }
}
