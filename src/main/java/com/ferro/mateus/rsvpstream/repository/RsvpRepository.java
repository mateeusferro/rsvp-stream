package com.ferro.mateus.rsvpstream.repository;

import com.ferro.mateus.rsvpstream.domain.Rsvp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RsvpRepository extends JpaRepository<Rsvp, UUID> {
}
