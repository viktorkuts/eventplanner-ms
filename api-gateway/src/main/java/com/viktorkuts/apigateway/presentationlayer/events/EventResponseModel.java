package com.viktorkuts.apigateway.presentationlayer.events;

import com.viktorkuts.apigateway.presentationlayer.tickets.TicketResponseModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventResponseModel {
    String eventId;
    LocalDateTime startsAt;
    LocalDateTime endsAt;
    String performerId;
    String performerFirstName;
    String performerLastName;
    String venueId;
    String venueName;
    List<TicketResponseModel> tickets;
}