package com.viktorkuts.events.domainclientlayer.tickets;

import lombok.*;

@Data
@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TicketModel {
    String ticketId;
    String user;
}
