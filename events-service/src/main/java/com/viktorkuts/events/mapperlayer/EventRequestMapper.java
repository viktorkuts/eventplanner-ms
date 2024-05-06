package com.viktorkuts.events.mapperlayer;

import com.sun.jdi.request.EventRequest;
import com.viktorkuts.events.datalayer.Event;
import com.viktorkuts.events.datalayer.EventIdentifier;
import com.viktorkuts.events.domainclientlayer.customers.CustomerModel;
import com.viktorkuts.events.domainclientlayer.tickets.TicketModel;
import com.viktorkuts.events.domainclientlayer.venues.VenueModel;
import com.viktorkuts.events.presentationlayer.models.EventRequestModel;
import com.viktorkuts.events.presentationlayer.models.EventResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EventRequestMapper {
    @Mapping(target = "id", ignore = true)
    Event requestToEntity(EventRequestModel eventRequest, EventIdentifier eventIdentifier);
//    Event requestToEntity(EventRequest eventRequest, EventIdentifier eventIdentifier, CustomerModel customerModel, TicketModel ticketModel, VenueModel venueModel);
}
