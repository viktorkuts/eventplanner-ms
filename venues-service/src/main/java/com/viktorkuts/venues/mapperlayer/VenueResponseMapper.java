package com.viktorkuts.venues.mapperlayer;

import com.viktorkuts.venues.datalayer.Venue;
import com.viktorkuts.venues.presentationlayer.models.VenueResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VenueResponseMapper {
    @Mapping(expression = "java(venue.getVenueIdentifier().getVenueId())", target = "venueId")
    VenueResponseModel entityToModel(Venue venue);
    List<VenueResponseModel> entitiesToModels(List<Venue> venues);
}
