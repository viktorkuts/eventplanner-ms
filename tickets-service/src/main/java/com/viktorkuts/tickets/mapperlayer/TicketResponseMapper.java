package com.viktorkuts.tickets.mapperlayer;

import com.viktorkuts.tickets.datalayer.Ticket;
import com.viktorkuts.tickets.presentationlayer.models.TicketResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketResponseMapper {
    @Mapping(source = "java(ticket.getTicketIdentifier().getTicketId())", target = "ticketId")
    TicketResponseModel entityToModel(Ticket ticket);
    List<TicketResponseModel> entitiesToModels(List<Ticket> tickets);
}
