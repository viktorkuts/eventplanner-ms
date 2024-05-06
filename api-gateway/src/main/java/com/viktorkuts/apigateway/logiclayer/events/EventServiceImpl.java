package com.viktorkuts.apigateway.logiclayer.events;

import com.viktorkuts.apigateway.domainclientlayer.events.EventClient;
import com.viktorkuts.apigateway.mapperlayer.events.EventResponseMapper;
import com.viktorkuts.apigateway.mapperlayer.tickets.TicketResponseMapper;
import com.viktorkuts.apigateway.presentationlayer.events.EventRequestModel;
import com.viktorkuts.apigateway.presentationlayer.events.EventResponseModel;
import com.viktorkuts.apigateway.presentationlayer.tickets.TicketResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventClient eventClient;
    private final EventResponseMapper eventResponseMapper;
    private final TicketResponseMapper ticketResponseMapper;

    public EventServiceImpl(EventClient eventClient, EventResponseMapper eventResponseMapper, TicketResponseMapper ticketResponseMapper) {
        this.eventClient = eventClient;
        this.eventResponseMapper = eventResponseMapper;
        this.ticketResponseMapper = ticketResponseMapper;
    }

    @Override
    public List<EventResponseModel> getAllEvents() {
        return eventResponseMapper.processModels(eventClient.getAllEvents());
    }

    @Override
    public EventResponseModel getEventById(String eventId) {
        return eventResponseMapper.processModel(eventClient.getEvent(eventId));
    }

    @Override
    public EventResponseModel createEvent(EventRequestModel eventRequestModel) {
        return eventResponseMapper.processModel(eventClient.createEvent(eventRequestModel));
    }

    @Override
    public EventResponseModel updateEvent(EventRequestModel eventRequestModel, String eventId){
        return eventResponseMapper.processModel(eventClient.updateEvent(eventRequestModel, eventId));
    }

    @Override
    public void deleteEvent(String eventId) {
        eventClient.deleteEvent(eventId);
    }

    @Override
    public List<EventResponseModel> getAllEventsForPerformer(String customerId){
        return eventResponseMapper.processModels(eventClient.getAllEventsForPerformer(customerId));
    }

    @Override
    public List<TicketResponseModel> getAllTicketsForCustomer(String customerId){
        return ticketResponseMapper.processResponseModels(eventClient.getAllTicketsForCustomer(customerId));
    }
    @Override
    public List<EventResponseModel> getAllEventsForVenue(String venueId){
        return eventResponseMapper.processModels(eventClient.getEventsForVenue(venueId));
    }

}
