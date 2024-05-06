package com.viktorkuts.events.presentationlayer;

import com.viktorkuts.events.datalayer.Event;
import com.viktorkuts.events.domainclientlayer.tickets.TicketModel;
import com.viktorkuts.events.domainclientlayer.venues.VenueModel;
import com.viktorkuts.events.logiclayer.EventService;
import com.viktorkuts.events.presentationlayer.models.EventRequestModel;
import com.viktorkuts.events.presentationlayer.models.EventResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("performer/{customerId}")
    public ResponseEntity<List<EventResponseModel>> getEventsForPerformer(@PathVariable String customerId) {
        return ResponseEntity.ok(eventService.getAllEventsForPerformer(customerId));
    }

    @GetMapping("tickets/{customerId}")
    public ResponseEntity<List<TicketModel>> getTicketsForUser(@PathVariable String customerId) {
        return ResponseEntity.ok(eventService.getTicketsForUser(customerId));
    }

    @GetMapping("venue/{venueId}")
    public ResponseEntity<List<EventResponseModel>> getEventsForVenue(@PathVariable String venueId) {
        return ResponseEntity.ok(eventService.getEventsForVenue(venueId));
    }

    @GetMapping()
    public ResponseEntity<List<EventResponseModel>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("{eventId}")
    public ResponseEntity<EventResponseModel> getEvent(@PathVariable String eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @PostMapping()
    public ResponseEntity<EventResponseModel> postEvent(@RequestBody EventRequestModel eventRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(eventRequestModel));
    }

    @PutMapping("{eventId}")
    public ResponseEntity<EventResponseModel> editEvent(@RequestBody EventRequestModel eventRequestModel, @PathVariable String eventId) {
        return ResponseEntity.ok(eventService.updateEvent(eventRequestModel, eventId));
    }

    @DeleteMapping("{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

