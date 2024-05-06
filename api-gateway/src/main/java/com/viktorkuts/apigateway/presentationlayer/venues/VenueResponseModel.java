package com.viktorkuts.apigateway.presentationlayer.venues;

import com.viktorkuts.apigateway.domainclientlayer.customers.Address;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VenueResponseModel extends RepresentationModel<VenueResponseModel> {
    String venueId;
    String venueName;
    Address address;
    LocalDateTime availableStart;
    LocalDateTime availableEnd;
    Integer maxBlockAllocation;
    Integer capacity;
}
