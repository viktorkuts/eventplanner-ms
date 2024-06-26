package com.viktorkuts.events.presentationlayer.models;

import com.viktorkuts.events.domainclientlayer.tickets.TicketModel;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventResponseModel extends RepresentationModel<EventResponseModel> {
    String eventId;
    LocalDateTime startsAt;
    LocalDateTime endsAt;
    String performerId;
    String performerFirstName;
    String performerLastName;
    String venueId;
    String venueName;
    List<TicketModel> tickets;
}