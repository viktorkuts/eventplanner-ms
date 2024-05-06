package com.viktorkuts.apigateway.logiclayer.events;

import com.viktorkuts.apigateway.presentationlayer.events.EventRequestModel;
import com.viktorkuts.apigateway.presentationlayer.events.EventResponseModel;
import com.viktorkuts.apigateway.presentationlayer.tickets.TicketResponseModel;

import java.util.List;

public interface EventService {
    List<EventResponseModel> getAllEvents();
    EventResponseModel getEventById(String eventId);
    EventResponseModel createEvent(EventRequestModel eventRequestModel);
    EventResponseModel updateEvent(EventRequestModel eventRequestModel, String eventId);
    void deleteEvent(String eventId);

    List<EventResponseModel> getAllEventsForPerformer(String customerId);
    List<TicketResponseModel> getAllTicketsForCustomer(String customerId);
    List<EventResponseModel> getAllEventsForVenue(String venueId);
}
