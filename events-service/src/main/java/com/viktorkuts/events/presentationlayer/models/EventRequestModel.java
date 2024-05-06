package com.viktorkuts.events.presentationlayer.models;

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
    List<String> tickets;
}
