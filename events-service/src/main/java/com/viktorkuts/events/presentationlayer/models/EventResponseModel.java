package com.viktorkuts.events.presentationlayer.models;

import lombok.*;

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
    List<String> tickets;
}