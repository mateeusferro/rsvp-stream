package com.ferro.mateus.rsvpstream.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "rsvp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rsvp {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    private String attendeeName;
    private Boolean attending;
}
