package com.viktorkuts.apigateway.presentationlayer.customers;

import com.viktorkuts.apigateway.domainclientlayer.customers.Address;
import com.viktorkuts.apigateway.domainclientlayer.customers.CustomerType;
import com.viktorkuts.apigateway.domainclientlayer.customers.PhoneNumber;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerResponseModel extends RepresentationModel<CustomerResponseModel> {
    String customerId;
    CustomerType customerType;
    String firstName;
    String lastName;
    String emailAddress;
    Address address;
    List<PhoneNumber> phoneNumbers;
}
