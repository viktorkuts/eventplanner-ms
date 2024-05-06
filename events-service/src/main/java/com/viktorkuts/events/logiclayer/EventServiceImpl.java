package com.viktorkuts.events.logiclayer;

import com.viktorkuts.events.datalayer.Event;
import com.viktorkuts.events.datalayer.EventRepository;
import com.viktorkuts.events.domainclientlayer.customers.CustomerClient;
import com.viktorkuts.events.domainclientlayer.customers.CustomerModel;
import com.viktorkuts.events.domainclientlayer.tickets.TicketClient;
import com.viktorkuts.events.domainclientlayer.tickets.TicketModel;
import com.viktorkuts.events.mapperlayer.EventResponseMapper;
import com.viktorkuts.events.presentationlayer.models.EventResponseModel;
import com.viktorkuts.events.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventResponseMapper eventResponseMapper;
    private final CustomerClient customerClient;
    private final TicketClient ticketClient;

    public EventServiceImpl(EventRepository eventRepository, EventResponseMapper eventResponseMapper, CustomerClient customerClient, TicketClient ticketClient) {
        this.eventRepository = eventRepository;
        this.eventResponseMapper = eventResponseMapper;
        this.customerClient = customerClient;
        this.ticketClient = ticketClient;
    }

    public List<EventResponseModel> getAllEventsForPerformer(String customerId){
        // Check if customerId is performer, return custom exception if not
        CustomerModel foundCustomer = customerClient.getCustomer(customerId);

        if(foundCustomer == null){
            throw new NotFoundException("Invalid customerId: " + customerId);
        }

        if(!foundCustomer.getCustomerType().toString().equals("PERFORMER")){
            throw new NotFoundException("Customer is not of type performer!");
        }

        List<Event> events = eventRepository.findEventsByPerformer_CustomerId(customerId);
        return eventResponseMapper.entitiesToModels(events);
    }
    @Override
    public List<TicketModel> getTicketsForUser(String customerId){
        CustomerModel foundCustomer = customerClient.getCustomer(customerId);
        if(foundCustomer == null){
            throw new NotFoundException("Invalid customerId: " + customerId);
        }
        List<TicketModel> tickets = new ArrayList<>();
        for(TicketModel ticket: ticketClient.getAllTickets()){
            if(ticket.getUser().equals(foundCustomer.getCustomerId())){
                tickets.add(ticket);
            }
        }
        return tickets;
    }
}
