package com.viktorkuts.apigateway.logiclayer.tickets;

import com.viktorkuts.apigateway.domainclientlayer.tickets.TicketClient;
import com.viktorkuts.apigateway.mapperlayer.tickets.TicketResponseMapper;
import com.viktorkuts.apigateway.presentationlayer.tickets.TicketRequestModel;
import com.viktorkuts.apigateway.presentationlayer.tickets.TicketResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService{
    private final TicketClient ticketClient;
    private final TicketResponseMapper resMapper;

    private TicketServiceImpl(TicketClient ticketClient, TicketResponseMapper ticketResponseMapper) {
        this.ticketClient = ticketClient;
        this.resMapper = ticketResponseMapper;
    }

    @Override
    public List<TicketResponseModel> getAllTickets(){
        return resMapper.processResponseModels(ticketClient.getAllTickets());
    }

    @Override
    public TicketResponseModel getTicket(String ticketId){
        return resMapper.processResponseModel(ticketClient.getTicket(ticketId));
    }

    @Override
    public TicketResponseModel addTicket(TicketRequestModel requestModel){
        return resMapper.processResponseModel(ticketClient.addTicket(requestModel));
    }

    @Override
    public TicketResponseModel editTicket(TicketRequestModel requestModel, String ticketId){
        return resMapper.processResponseModel(ticketClient.editTicket(requestModel, ticketId));
    }

    @Override
    public void deleteTicket(String ticketId){
        ticketClient.deleteTicket(ticketId);
    }

}
