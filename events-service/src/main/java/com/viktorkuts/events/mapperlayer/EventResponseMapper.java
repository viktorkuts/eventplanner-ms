package com.viktorkuts.events.mapperlayer;

import com.viktorkuts.events.datalayer.Event;
import com.viktorkuts.events.domainclientlayer.tickets.TicketModel;
import com.viktorkuts.events.presentationlayer.models.EventResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EventResponseMapper {
    @Named("fromTicketListToTicketIdList")
    default List<String> fromTicketListToTicketIdList(List<TicketModel> tickets){
        List<String> ticketIds = new ArrayList<>();
        for (TicketModel ticket: tickets) {
            ticketIds.add(ticket.getTicketId());
        }
        return ticketIds;
    }
    @Mappings({
            @Mapping(expression = "java(event.getEventIdentifier().getEventId())", target = "eventId"),
            @Mapping(expression = "java(event.getPerformer().getCustomerId())", target = "performerId"),
            @Mapping(expression = "java(event.getPerformer().getFirstName())", target = "performerFirstName"),
            @Mapping(expression = "java(event.getPerformer().getLastName())", target = "performerLastName"),
            @Mapping(expression = "java(event.getVenue().getVenueId())", target = "venueId"),
            @Mapping(expression = "java(event.getVenue().getVenueName())", target = "venueName"),
            @Mapping(source = "tickets", target = "tickets", qualifiedByName = "fromTicketListToTicketIdList")
    })
    EventResponseModel entityToModel(Event event);
    List<EventResponseModel> entitiesToModels(List<Event> events);
}
