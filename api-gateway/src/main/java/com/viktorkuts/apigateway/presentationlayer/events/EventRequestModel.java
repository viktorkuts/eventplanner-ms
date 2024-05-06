package com.viktorkuts.apigateway.presentationlayer.events;

import com.viktorkuts.apigateway.presentationlayer.tickets.TicketResponseModel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class EventRequestModel {
    LocalDateTime startsAt;
    LocalDateTime endsAt;
    String performerId;
    String venueId;
    List<TicketResponseModel> tickets;
}
