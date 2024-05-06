package com.viktorkuts.venues.presentationlayer.models;

import com.viktorkuts.venues.datalayer.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VenueResponseModel {
    private String venueId;
    private String venueName;
    private Address address;
    private LocalDateTime availableStart;
    private LocalDateTime availableEnd;
    private Integer maxBlockAllocation;
    private Integer capacity;
}
