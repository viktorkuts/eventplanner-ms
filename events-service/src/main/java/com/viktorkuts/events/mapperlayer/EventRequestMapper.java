package com.viktorkuts.events.mapperlayer;

import com.sun.jdi.request.EventRequest;
import com.viktorkuts.events.datalayer.Event;
import com.viktorkuts.events.datalayer.EventIdentifier;
import com.viktorkuts.events.domainclientlayer.customers.CustomerModel;
import com.viktorkuts.events.domainclientlayer.tickets.TicketModel;
import com.viktorkuts.events.domainclientlayer.venues.VenueModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EventRequestMapper {

//    Event requestToEntity(EventRequest eventRequest, EventIdentifier eventIdentifier, CustomerModel customerModel, TicketModel ticketModel, VenueModel venueModel);
}
