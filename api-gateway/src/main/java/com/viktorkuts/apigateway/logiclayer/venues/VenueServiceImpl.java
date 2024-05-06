package com.viktorkuts.apigateway.logiclayer.venues;

import com.viktorkuts.apigateway.domainclientlayer.venues.VenueClient;
import com.viktorkuts.apigateway.mapperlayer.venues.VenueResponseMapper;
import com.viktorkuts.apigateway.presentationlayer.venues.VenueRequestModel;
import com.viktorkuts.apigateway.presentationlayer.venues.VenueResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl implements VenueService{
    private final VenueClient venueClient;
    private final VenueResponseMapper resMapper;

    public VenueServiceImpl(VenueClient venueClient, VenueResponseMapper resMapper) {
        this.venueClient = venueClient;
        this.resMapper = resMapper;
    }

    @Override
    public List<VenueResponseModel> getAllVenues(){
        return resMapper.processResponseModels(venueClient.getAllVenues());
    }

    @Override
    public VenueResponseModel getVenue(String venueId){
        return resMapper.processResponseModel(venueClient.getVenue(venueId));
    }

    @Override
    public VenueResponseModel addVenue(VenueRequestModel requestModel){
        return resMapper.processResponseModel(venueClient.addVenue(requestModel));
    }

    @Override
    public VenueResponseModel editVenue(VenueRequestModel requestModel, String venueId){
        return resMapper.processResponseModel(venueClient.editVenue(requestModel, venueId));
    }

    @Override
    public void deleteVenue(String venueId){
        venueClient.deleteVenue(venueId);
    }
}
