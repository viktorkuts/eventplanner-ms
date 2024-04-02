package com.viktorkuts.customers.presentationlayer.models;

import java.util.List;

import com.viktorkuts.customers.datalayer.Address;
import com.viktorkuts.customers.datalayer.CustomerType;
import com.viktorkuts.customers.datalayer.PhoneNumber;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerResponseModel{
	private String customerId;
	private CustomerType customerType;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private Address address;
	private List<PhoneNumber> phoneNumbers;
}
