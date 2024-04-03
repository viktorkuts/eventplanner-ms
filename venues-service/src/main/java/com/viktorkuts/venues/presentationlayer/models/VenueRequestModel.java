package com.viktorkuts.venues.presentationlayer.models;

import com.viktorkuts.venues.datalayer.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class VenueRequestModel {
    private String venueName;
    private Address address;
    private LocalDateTime availableStart;
    private LocalDateTime availableEnd;
    private Integer maxBlockAllocation;
    private Integer capacity;
}
