package com.ferro.mateus.rsvpstream.service;

import com.ferro.mateus.rsvpstream.domain.Rsvp;

import java.util.UUID;

public interface RsvpService {
    void processRsvp(UUID eventUuid, Rsvp rsvp);
}
