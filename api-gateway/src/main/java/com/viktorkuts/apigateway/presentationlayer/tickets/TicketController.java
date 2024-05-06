package com.viktorkuts.apigateway.presentationlayer.tickets;

import com.viktorkuts.apigateway.logiclayer.tickets.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TicketResponseModel>> getAllTickets(){
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping(value = "{ticketId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketResponseModel> getTicketById(@PathVariable String ticketId){
        return ResponseEntity.ok(ticketService.getTicket(ticketId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketResponseModel> addTicket(@RequestBody TicketRequestModel requestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.addTicket(requestModel));
    }

    @PutMapping(value = "{ticketId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketResponseModel> editTicket(@RequestBody TicketRequestModel requestModel, @PathVariable String ticketId){
        return ResponseEntity.ok(ticketService.editTicket(requestModel, ticketId));
    }

    @DeleteMapping("{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable String ticketId){
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


}
