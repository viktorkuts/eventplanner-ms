package com.viktorkuts.venues.mapperlayer;

import com.viktorkuts.venues.datalayer.Venue;
import com.viktorkuts.venues.datalayer.VenueIdentifier;
import com.viktorkuts.venues.presentationlayer.models.VenueRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VenueRequestMapper {
    @Mapping(target = "id", ignore = true)
    Venue modelToEntity(VenueRequestModel requestModel, VenueIdentifier venueIdentifier);
}
