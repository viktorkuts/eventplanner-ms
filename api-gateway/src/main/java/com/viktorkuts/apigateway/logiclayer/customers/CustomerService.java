package com.viktorkuts.apigateway.logiclayer.customers;

import com.viktorkuts.apigateway.presentationlayer.customers.CustomerRequestModel;
import com.viktorkuts.apigateway.presentationlayer.customers.CustomerResponseModel;

import java.util.List;

public interface CustomerService {
    List<CustomerResponseModel> getAllCustomers();
    CustomerResponseModel getCustomer(String customerId);
    CustomerResponseModel addCustomer(CustomerRequestModel requestModel);
    CustomerResponseModel editCustomer(CustomerRequestModel requestModel, String customerId);
    void deleteCustomer(String customerId);
}
