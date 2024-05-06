package com.viktorkuts.apigateway.presentationlayer.customers;

import com.viktorkuts.apigateway.domainclientlayer.customers.Address;
import com.viktorkuts.apigateway.domainclientlayer.customers.CustomerType;
import com.viktorkuts.apigateway.domainclientlayer.customers.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CustomerRequestModel {
    private CustomerType customerType;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Address address;
    private List<PhoneNumber> phoneNumbers;
}

