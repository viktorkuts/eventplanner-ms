package com.viktorkuts.events.presentationlayer;

import com.viktorkuts.events.domainclientlayer.tickets.TicketModel;
import com.viktorkuts.events.logiclayer.EventService;
import com.viktorkuts.events.presentationlayer.models.EventResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("{customerId}/events")
    public ResponseEntity<List<EventResponseModel>> getEventsForPerformer(@PathVariable String customerId) {
        return ResponseEntity.ok(eventService.getAllEventsForPerformer(customerId));
    }

    @GetMapping("{customerId}/tickets")
    public ResponseEntity<List<TicketModel>> getTicketsForUser(@PathVariable String customerId) {
        return ResponseEntity.ok(eventService.getTicketsForUser(customerId));
    }
}

