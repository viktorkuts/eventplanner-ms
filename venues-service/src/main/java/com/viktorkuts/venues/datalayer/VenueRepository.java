package com.viktorkuts.venues.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Integer> {
    Venue findVenueByVenueIdentifier_VenueId(String venueId);
}
