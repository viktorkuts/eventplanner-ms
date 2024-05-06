package com.viktorkuts.events.logiclayer;

import com.viktorkuts.events.datalayer.Event;
import com.viktorkuts.events.datalayer.EventIdentifier;
import com.viktorkuts.events.datalayer.EventRepository;
import com.viktorkuts.events.domainclientlayer.customers.CustomerClient;
import com.viktorkuts.events.domainclientlayer.customers.CustomerModel;
import com.viktorkuts.events.domainclientlayer.tickets.TicketClient;
import com.viktorkuts.events.domainclientlayer.tickets.TicketModel;
import com.viktorkuts.events.domainclientlayer.venues.VenueClient;
import com.viktorkuts.events.domainclientlayer.venues.VenueModel;
import com.viktorkuts.events.mapperlayer.EventRequestMapper;
import com.viktorkuts.events.mapperlayer.EventResponseMapper;
import com.viktorkuts.events.presentationlayer.models.EventRequestModel;
import com.viktorkuts.events.presentationlayer.models.EventResponseModel;
import com.viktorkuts.events.utils.exceptions.InUseException;
import com.viktorkuts.events.utils.exceptions.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventResponseMapper eventResponseMapper;
    private final EventRequestMapper eventRequestMapper;
    private final CustomerClient customerClient;
    private final TicketClient ticketClient;
    private final VenueClient venueClient;

    public EventServiceImpl(EventRepository eventRepository, EventResponseMapper eventResponseMapper, EventRequestMapper eventRequestMapper, CustomerClient customerClient, TicketClient ticketClient, VenueClient venueClient) {
        this.eventRepository = eventRepository;
        this.eventResponseMapper = eventResponseMapper;
        this.eventRequestMapper = eventRequestMapper;
        this.customerClient = customerClient;
        this.ticketClient = ticketClient;
        this.venueClient = venueClient;
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

    @Override
    public List<EventResponseModel> getEventsForVenue(String venueId){
        VenueModel foundVenue = venueClient.getVenue(venueId);
        if(foundVenue == null){
            throw new NotFoundException("Invalid venueId: " + venueId);
        }
        List<Event> events = new ArrayList<>();
        for(Event evt : eventRepository.findAll()){
            if(evt.getVenue().getVenueId().equals(foundVenue.getVenueId())){
                events.add(evt);
            }
        }
        return eventResponseMapper.entitiesToModels(events);
    }

    public List<EventResponseModel> getAllEvents(){
        return eventResponseMapper.entitiesToModels(eventRepository.findAll());
    }

    public EventResponseModel getEventById(String eventId){
        Event foundEvent = eventRepository.findEventByEventIdentifier_EventId(eventId);
        if(foundEvent == null){
            throw new NotFoundException("Invalid eventId: " + eventId);
        }
        return eventResponseMapper.entityToModel(foundEvent);
    }

    public EventResponseModel createEvent(EventRequestModel eventRequestModel){
        Event evt = eventRequestMapper.requestToEntity(eventRequestModel, new EventIdentifier());
        eventRepository.save(evt);
        return eventResponseMapper.entityToModel(evt);
    }

    public EventResponseModel updateEvent(EventRequestModel eventRequestModel, String eventId){
        Event foundEvent = eventRepository.findEventByEventIdentifier_EventId(eventId);
        if(foundEvent == null){
            throw new NotFoundException("Invalid eventId: " + eventId);
        }

        Event updatedEvent = eventRequestMapper.requestToEntity(eventRequestModel, new EventIdentifier());
        updatedEvent.setId(foundEvent.getId());
        eventRepository.save(updatedEvent);
        return eventResponseMapper.entityToModel(updatedEvent);
    }

    public void deleteEvent(String eventId){
        Event foundEvent = eventRepository.findEventByEventIdentifier_EventId(eventId);
        if(foundEvent == null){
            throw new NotFoundException("Invalid eventId: " + eventId);
        }
        try{
            eventRepository.delete(foundEvent);
        }catch (DataIntegrityViolationException ex){
            throw new InUseException("Event is used!");
        }
    }


}
