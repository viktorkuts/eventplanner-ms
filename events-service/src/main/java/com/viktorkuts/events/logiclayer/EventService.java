package com.viktorkuts.events.logiclayer;

import com.viktorkuts.events.domainclientlayer.tickets.TicketModel;
import com.viktorkuts.events.presentationlayer.models.EventRequestModel;
import com.viktorkuts.events.presentationlayer.models.EventResponseModel;

import java.util.List;

public interface EventService {
    public List<EventResponseModel> getAllEventsForPerformer(String customerId);
    public List<TicketModel> getTicketsForUser(String customerId);
    public List<EventResponseModel> getEventsForVenue(String venueId);
    public List<EventResponseModel> getAllEvents();
    public EventResponseModel getEventById(String eventId);
    public EventResponseModel createEvent(EventRequestModel eventRequestModel);
    public EventResponseModel updateEvent(EventRequestModel eventRequestModel, String eventId);
    public void deleteEvent(String eventId);
}
