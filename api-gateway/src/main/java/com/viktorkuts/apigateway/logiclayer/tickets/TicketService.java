package com.viktorkuts.apigateway.logiclayer.tickets;

import com.viktorkuts.apigateway.presentationlayer.tickets.TicketRequestModel;
import com.viktorkuts.apigateway.presentationlayer.tickets.TicketResponseModel;

import java.util.List;

public interface TicketService {
    List<TicketResponseModel> getAllTickets();
    TicketResponseModel getTicket(String ticketId);
    TicketResponseModel addTicket(TicketRequestModel requestModel);
    TicketResponseModel editTicket(TicketRequestModel ticketRequestModel, String ticketId);
    void deleteTicket(String ticketId);
}
