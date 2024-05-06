package com.viktorkuts.apigateway.mapperlayer.tickets;

import com.viktorkuts.apigateway.presentationlayer.customers.CustomerController;
import com.viktorkuts.apigateway.presentationlayer.customers.CustomerResponseModel;
import com.viktorkuts.apigateway.presentationlayer.tickets.TicketController;
import com.viktorkuts.apigateway.presentationlayer.tickets.TicketResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface TicketResponseMapper {
    List<TicketResponseModel> processResponseModels(List<TicketResponseModel> responseModels);
    TicketResponseModel processResponseModel(TicketResponseModel ticketResponseModel);

    @AfterMapping
    default void addLinks(@MappingTarget TicketResponseModel ticketResponseModel){
        Link selfLink = linkTo(methodOn(TicketController.class)
                .getTicketById(ticketResponseModel.getTicketId()))
                .withSelfRel();
        ticketResponseModel.add(selfLink);

        Link allLink = linkTo(methodOn(TicketController.class).getAllTickets())
                .withRel("tickets");
        ticketResponseModel.add(allLink);

        if(ticketResponseModel.getUser() != null){
            if(!ticketResponseModel.getUser().isBlank()){
                ticketResponseModel.add(linkTo(methodOn(CustomerController.class).getCustomerById(ticketResponseModel.getUser()))
                        .withRel("associated_customer"));
            }
        }
    }
}
