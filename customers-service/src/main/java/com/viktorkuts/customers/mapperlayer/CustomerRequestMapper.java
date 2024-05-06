package com.viktorkuts.customers.mapperlayer;

import com.viktorkuts.customers.datalayer.Customer;
import com.viktorkuts.customers.datalayer.CustomerIdentifier;
import com.viktorkuts.customers.datalayer.PhoneNumber;
import com.viktorkuts.customers.datalayer.PhoneType;
import com.viktorkuts.customers.presentationlayer.models.CustomerRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {

//    @Named(value = "mapPhoneNumbers")
//    default List<PhoneNumber> mapPhoneNumbers(List<PhoneNumber> phoneNumbers){
//        for (PhoneNumber phoneNumber : phoneNumbers) {
//            if(phoneNumber.getType() == null){
//                phoneNumber.setType(PhoneType.UNCATEGORIZED);
//            }
//        }
//        return phoneNumbers;
//    }
    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "phoneNumbers", source = "customerRequestModel", qualifiedByName = "mapPhoneNumbers")
    Customer modelToEntity(CustomerRequestModel customerRequestModel, CustomerIdentifier customerIdentifier);
}
