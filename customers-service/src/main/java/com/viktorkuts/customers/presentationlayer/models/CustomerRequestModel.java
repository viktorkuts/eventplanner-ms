package com.viktorkuts.customers.presentationlayer.models;

import com.viktorkuts.customers.datalayer.Address;
import com.viktorkuts.customers.datalayer.CustomerType;
import com.viktorkuts.customers.datalayer.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestModel {
    private CustomerType customerType;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Address address;
    private List<PhoneNumber> phoneNumbers;
}
