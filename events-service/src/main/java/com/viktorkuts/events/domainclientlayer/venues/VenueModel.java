package com.viktorkuts.events.domainclientlayer.venues;

import com.viktorkuts.events.domainclientlayer.customers.Address;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VenueModel {
    String venueId;
    String venueName;
}
