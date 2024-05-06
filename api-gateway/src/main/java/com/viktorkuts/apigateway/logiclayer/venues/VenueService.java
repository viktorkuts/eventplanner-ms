package com.viktorkuts.apigateway.logiclayer.venues;

import com.viktorkuts.apigateway.presentationlayer.venues.VenueRequestModel;
import com.viktorkuts.apigateway.presentationlayer.venues.VenueResponseModel;

import java.util.List;

public interface VenueService {
    List<VenueResponseModel> getAllVenues();
    VenueResponseModel getVenue(String venueId);
    VenueResponseModel addVenue(VenueRequestModel venueRequestModel);
    VenueResponseModel editVenue(VenueRequestModel venueRequestModel, String venueId);
    void deleteVenue(String venueId);
}
