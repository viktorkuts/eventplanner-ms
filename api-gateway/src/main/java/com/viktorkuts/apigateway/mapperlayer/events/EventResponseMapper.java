package com.viktorkuts.apigateway.mapperlayer.events;

import com.viktorkuts.apigateway.presentationlayer.events.EventResponseModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventResponseMapper {
    List<EventResponseModel> processModels(List<EventResponseModel> eventResponseModels);
    EventResponseModel processModel(EventResponseModel eventResponseModel);
}
