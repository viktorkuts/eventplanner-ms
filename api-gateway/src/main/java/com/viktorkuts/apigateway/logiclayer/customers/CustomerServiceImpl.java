package com.viktorkuts.apigateway.logiclayer.customers;

import com.viktorkuts.apigateway.domainclientlayer.customers.CustomerClient;
import com.viktorkuts.apigateway.mapperlayer.customers.CustomerResponseMapper;
import com.viktorkuts.apigateway.presentationlayer.customers.CustomerRequestModel;
import com.viktorkuts.apigateway.presentationlayer.customers.CustomerResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerClient customerClient;
    private final CustomerResponseMapper resMapper;

    private CustomerServiceImpl(CustomerClient customerClient, CustomerResponseMapper responseMapper){
        this.customerClient = customerClient;
        this.resMapper = responseMapper;
    }

    @Override
    public List<CustomerResponseModel> getAllCustomers(){
        return resMapper.processResponseModels(customerClient.getAllCustomers());
    }

    @Override
    public CustomerResponseModel getCustomer(String customerId){
        return resMapper.processResponseModel(customerClient.getCustomer(customerId));
    }

    @Override
    public CustomerResponseModel addCustomer(CustomerRequestModel requestModel){
        return customerClient.postCustomer(requestModel);
    }

    @Override
    public CustomerResponseModel editCustomer(CustomerRequestModel requestModel, String customerId){
        return customerClient.editCustomer(requestModel, customerId);
    }

    @Override
    public void deleteCustomer(String customerId){
        customerClient.deleteCustomer(customerId);
    }
}
