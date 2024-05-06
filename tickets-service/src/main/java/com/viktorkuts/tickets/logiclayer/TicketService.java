package com.viktorkuts.tickets.logiclayer;

import com.viktorkuts.tickets.presentationlayer.models.TicketRequestModel;
import com.viktorkuts.tickets.presentationlayer.models.TicketResponseModel;

import java.util.List;

public interface TicketService {
    List<TicketResponseModel> getTickets();
    TicketResponseModel getTicket(String ticketId);
    TicketResponseModel createTicket(TicketRequestModel ticketRequestModel);
    TicketResponseModel updateTicket(String ticketId, TicketRequestModel ticketRequestModel);
    void deleteTicket(String ticketId);
}
