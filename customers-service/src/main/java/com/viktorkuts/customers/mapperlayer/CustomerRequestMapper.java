package com.viktorkuts.customers.mapperlayer;

import com.viktorkuts.customers.datalayer.Customer;
import com.viktorkuts.customers.datalayer.CustomerIdentifier;
import com.viktorkuts.customers.presentationlayer.models.CustomerRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {
    @Mapping(target = "id", ignore = true)
    Customer modelToEntity(CustomerRequestModel customerRequestModel, CustomerIdentifier customerIdentifier);
}
