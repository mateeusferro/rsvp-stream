package com.ferro.mateus.rsvpstream.repository;

import com.ferro.mateus.rsvpstream.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
