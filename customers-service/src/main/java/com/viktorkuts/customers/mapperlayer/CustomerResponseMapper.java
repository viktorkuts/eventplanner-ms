package com.viktorkuts.customers.mapperlayer;

import com.viktorkuts.customers.datalayer.Customer;
import com.viktorkuts.customers.presentationlayer.models.CustomerResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerResponseMapper {
    @Mapping(expression = "java(customer.getCustomerIdentifier().getCustomerId())",target = "customerId")
    CustomerResponseModel entityToModel(Customer customer);
    List<CustomerResponseModel> entitiesToModels(List<Customer> customers);
}