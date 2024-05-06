package com.viktorkuts.customers.logiclayer;

import com.viktorkuts.customers.presentationlayer.models.CustomerRequestModel;
import com.viktorkuts.customers.presentationlayer.models.CustomerResponseModel;

import java.util.List;

public interface CustomerService {
    List<CustomerResponseModel> getCustomers();
    CustomerResponseModel getCustomer(String customerId);
    CustomerResponseModel addCustomer(CustomerRequestModel requestModel);
    CustomerResponseModel editCustomer(String customerId, CustomerRequestModel requestModel);
    void deleteCustomer(String customerId);
}
