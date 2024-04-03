package com.viktorkuts.venues.logiclayer;

import com.viktorkuts.venues.presentationlayer.models.VenueRequestModel;
import com.viktorkuts.venues.presentationlayer.models.VenueResponseModel;

import java.util.List;

public interface VenueService {
    List<VenueResponseModel> getVenues();
    VenueResponseModel getVenue(String venueId);
    VenueResponseModel createVenue(VenueRequestModel venueRequestModel);
    VenueResponseModel updateVenue(String venueId, VenueRequestModel venueRequestModel);
    void deleteVenue(String venueId);
}
