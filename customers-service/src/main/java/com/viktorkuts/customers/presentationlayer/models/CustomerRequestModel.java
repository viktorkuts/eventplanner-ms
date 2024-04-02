package com.viktorkuts.customers.presentationlayer.models;

import com.viktorkuts.customers.datalayer.Address;
import com.viktorkuts.customers.datalayer.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestModel {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Address address;
    private PhoneNumber phoneNumber;
}
