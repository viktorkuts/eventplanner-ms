package com.viktorkuts.tickets.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Ticket findTicketByTicketIdentifier_TicketId(String ticketId);
}
