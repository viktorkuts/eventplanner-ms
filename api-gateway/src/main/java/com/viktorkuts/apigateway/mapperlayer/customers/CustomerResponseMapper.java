package com.viktorkuts.apigateway.mapperlayer.customers;

import com.viktorkuts.apigateway.presentationlayer.customers.CustomerController;
import com.viktorkuts.apigateway.presentationlayer.customers.CustomerResponseModel;
import org.mapstruct.*;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CustomerResponseMapper {

    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    CustomerResponseModel processResponseModel(CustomerResponseModel customerResponseModel);
    List<CustomerResponseModel> processResponseModels(List<CustomerResponseModel> customerResponseModels);
    @AfterMapping
    default void addLinks(@MappingTarget CustomerResponseModel customerResponseModel){
        Link selfLink = linkTo(methodOn(CustomerController.class)
                .getCustomerById(customerResponseModel.getCustomerId()))
                .withSelfRel();

        customerResponseModel.add(selfLink);

        Link allLink = linkTo(methodOn(CustomerController.class)
                .getAllCustomers())
                .withRel("all_customers");

        customerResponseModel.add(allLink);
    }
}
