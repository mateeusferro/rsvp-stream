package com.ferro.mateus.rsvpstream.controller;

import com.ferro.mateus.rsvpstream.domain.Rsvp;
import com.ferro.mateus.rsvpstream.service.RsvpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/api/v1/rsvp")
public class RsvpController {
    private final Map<UUID, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final RsvpService rsvpService;

    public RsvpController(RsvpService rsvpService) {
        this.rsvpService = rsvpService;
    }

    @GetMapping("/{eventId}/updates")
    public SseEmitter listenForUpdates(@PathVariable UUID eventId) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(eventId, emitter);
        emitter.onCompletion(() -> emitters.remove(eventId));
        return emitter;
    }

    @PostMapping("/{eventId}")
    public ResponseEntity<String> rsvpForEvent(@PathVariable UUID eventId, @RequestBody Rsvp rsvp) {
        rsvpService.processRsvp(eventId, rsvp);

        sendUpdateToListeners(eventId, rsvp);
        return ResponseEntity.status(HttpStatus.CREATED).body("Processed RSVP");
    }

    private void sendUpdateToListeners(UUID eventId, Rsvp rsvp) {
        new Thread(() -> {
            SseEmitter emitter = emitters.get(eventId);
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event()
                            .name("rsvp-update")
                            .data("Attendee: " + rsvp.getAttendeeName() + " has RSVP'd: " + (rsvp.getAttending() ? "Yes" : "No")));
                    System.out.println("Update sent to listeners for event: " + eventId);
                } catch (IOException e) {
                    emitter.completeWithError(e);
                    System.err.println("Failed to send update: " + e.getMessage());
                }
            }
        }).start();
    }
}
