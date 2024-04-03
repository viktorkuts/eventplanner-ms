package com.viktorkuts.venues.datalayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class VenueIdentifier {
    @Column(name = "venueid")
    public String venueId;

    public VenueIdentifier() {
        this.venueId = UUID.randomUUID().toString();
    }
}
