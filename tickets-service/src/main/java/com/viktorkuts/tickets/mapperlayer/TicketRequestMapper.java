package com.viktorkuts.tickets.mapperlayer;

import com.viktorkuts.tickets.datalayer.Ticket;
import com.viktorkuts.tickets.datalayer.TicketIdentifier;
import com.viktorkuts.tickets.presentationlayer.models.TicketRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketRequestMapper {
    @Mapping(target = "id", ignore = true)
    Ticket modelToEntity(TicketRequestModel requestModel, TicketIdentifier ticketIdentifier);
}
