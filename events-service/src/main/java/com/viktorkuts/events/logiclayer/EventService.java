package com.viktorkuts.events.logiclayer;

import com.viktorkuts.events.domainclientlayer.tickets.TicketModel;
import com.viktorkuts.events.presentationlayer.models.EventResponseModel;

import java.util.List;

public interface EventService {
    public List<EventResponseModel> getAllEventsForPerformer(String customerId);
    public List<TicketModel> getTicketsForUser(String customerId);
}
