package com.viktorkuts.tickets.presentationlayer;

import com.viktorkuts.tickets.logiclayer.TicketService;
import com.viktorkuts.tickets.presentationlayer.models.TicketRequestModel;
import com.viktorkuts.tickets.presentationlayer.models.TicketResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TicketResponseModel>> getTickets(){
        return ResponseEntity.ok(ticketService.getTickets());
    }

    @GetMapping(value = "{ticketId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketResponseModel> getTicket(@PathVariable String ticketId){
        return ResponseEntity.ok(ticketService.getTicket(ticketId));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketResponseModel> createTicket(@RequestBody TicketRequestModel ticketRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(ticketRequestModel));
    }

    @PutMapping(value = "{ticketId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketResponseModel> updateTicket(@PathVariable String ticketId, @RequestBody TicketRequestModel ticketRequestModel){
        return ResponseEntity.ok(ticketService.updateTicket(ticketId, ticketRequestModel));
    }

    @DeleteMapping(value = "{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable String ticketId){
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
