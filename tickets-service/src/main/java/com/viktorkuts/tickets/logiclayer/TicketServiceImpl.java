package com.viktorkuts.tickets.logiclayer;

import com.viktorkuts.tickets.datalayer.Ticket;
import com.viktorkuts.tickets.datalayer.TicketIdentifier;
import com.viktorkuts.tickets.datalayer.TicketRepository;
import com.viktorkuts.tickets.mapperlayer.TicketRequestMapper;
import com.viktorkuts.tickets.mapperlayer.TicketResponseMapper;
import com.viktorkuts.tickets.presentationlayer.models.TicketRequestModel;
import com.viktorkuts.tickets.presentationlayer.models.TicketResponseModel;
import com.viktorkuts.tickets.utils.exceptions.AlreadyValidatedException;
import com.viktorkuts.tickets.utils.exceptions.InUseException;
import com.viktorkuts.tickets.utils.exceptions.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;
    private TicketResponseMapper ticketResponseMapper;
    private TicketRequestMapper ticketRequestMapper;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketResponseMapper ticketResponseMapper, TicketRequestMapper ticketRequestMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketResponseMapper = ticketResponseMapper;
        this.ticketRequestMapper = ticketRequestMapper;
    }

    @Override
    public List<TicketResponseModel> getTickets() {
        return ticketResponseMapper.entitiesToModels(ticketRepository.findAll());
    }

    @Override
    public TicketResponseModel getTicket(String ticketId) {
        Ticket foundTicket = ticketRepository.findTicketByTicketIdentifier_TicketId(ticketId);
        if(foundTicket == null){
            throw new NotFoundException("Unknown ticketId: " + ticketId);
        }
        return ticketResponseMapper.entityToModel(foundTicket);
    }

    @Override
    public TicketResponseModel createTicket(TicketRequestModel ticketRequestModel) {
        Ticket newTicket = ticketRequestMapper.modelToEntity(ticketRequestModel, new TicketIdentifier());
        ticketRepository.save(newTicket);
        return ticketResponseMapper.entityToModel(newTicket);
    }

    @Override
    public TicketResponseModel updateTicket(String ticketId, TicketRequestModel ticketRequestModel) {
        Ticket foundTicket = ticketRepository.findTicketByTicketIdentifier_TicketId(ticketId);
        if(foundTicket == null){
            throw new NotFoundException("Unknown ticketId: " + ticketId);
        }
        if(!ticketRequestModel.getValidated()){
            if(foundTicket.getValidated()){
                throw new AlreadyValidatedException("Ticket was already validated, cannot change value!");
            }
        }
        Ticket updatedTicket = ticketRequestMapper.modelToEntity(ticketRequestModel, foundTicket.getTicketIdentifier());
        updatedTicket.setId(foundTicket.getId());
        ticketRepository.save(updatedTicket);
        return ticketResponseMapper.entityToModel(updatedTicket);
    }

    @Override
    public void deleteTicket(String ticketId) {
        Ticket foundTicket = ticketRepository.findTicketByTicketIdentifier_TicketId(ticketId);
        if(foundTicket == null) {
            throw new NotFoundException("Unknown ticketId: " + ticketId);
        }
        try{
            ticketRepository.delete(foundTicket);
        } catch (DataIntegrityViolationException e){
            throw new InUseException("Ticket is in use by other resource!");
        }
    }
}
