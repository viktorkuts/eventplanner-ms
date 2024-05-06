package com.viktorkuts.apigateway.mapperlayer.venues;

import com.viktorkuts.apigateway.presentationlayer.venues.VenueController;
import com.viktorkuts.apigateway.presentationlayer.venues.VenueResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface VenueResponseMapper {
    List<VenueResponseModel> processResponseModels(List<VenueResponseModel> responseModels);
    VenueResponseModel processResponseModel(VenueResponseModel responseModel);

    @AfterMapping
    default void addLinks(@MappingTarget VenueResponseModel venueResponseModel){
        Link selfLink = linkTo(methodOn(VenueController.class)
                .getVenueById(venueResponseModel.getVenueId()))
                .withSelfRel();
        venueResponseModel.add(selfLink);

        Link allLink = linkTo(methodOn(VenueController.class)
                .getAllVenues())
                .withRel("venues");
        venueResponseModel.add(allLink);
    }
}
