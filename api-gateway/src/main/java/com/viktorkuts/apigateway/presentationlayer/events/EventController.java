package com.viktorkuts.apigateway.presentationlayer.events;

import com.viktorkuts.apigateway.logiclayer.events.EventService;
import com.viktorkuts.apigateway.presentationlayer.tickets.TicketResponseModel;
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

    @GetMapping()
    public ResponseEntity<List<EventResponseModel>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/performer/{customerId}")
    public ResponseEntity<List<EventResponseModel>> getAllEventsForPerformer(@PathVariable String customerId){
        return ResponseEntity.ok(eventService.getAllEventsForPerformer(customerId));
    }

    @GetMapping("/tickets/{customerId}")
    public ResponseEntity<List<TicketResponseModel>> getAllTicketsForCustomer(@PathVariable String customerId){
        return ResponseEntity.ok(eventService.getAllTicketsForCustomer(customerId));
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<List<EventResponseModel>> getAllEventsForVenue(@PathVariable String venueId){
        return ResponseEntity.ok(eventService.getAllEventsForVenue(venueId));
    }

    @GetMapping("{eventId}")
    public ResponseEntity<EventResponseModel> getEvent(@PathVariable String eventId){
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @PostMapping()
    public ResponseEntity<EventResponseModel> createEvent(@RequestBody EventRequestModel eventRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(eventRequestModel));
    }

    @PutMapping("{eventId}")
    public ResponseEntity<EventResponseModel> updateEvent(@RequestBody EventRequestModel eventRequestModel, @PathVariable String eventId){
        return ResponseEntity.ok(eventService.updateEvent(eventRequestModel, eventId));
    }

    @DeleteMapping("{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


}
