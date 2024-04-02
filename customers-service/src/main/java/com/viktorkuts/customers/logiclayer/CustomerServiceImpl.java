package com.viktorkuts.customers.logiclayer;

import com.viktorkuts.customers.datalayer.Customer;
import com.viktorkuts.customers.datalayer.CustomerIdentifier;
import com.viktorkuts.customers.datalayer.CustomerRepository;
import com.viktorkuts.customers.mapperlayer.CustomerRequestMapper;
import com.viktorkuts.customers.mapperlayer.CustomerResponseMapper;
import com.viktorkuts.customers.presentationlayer.models.CustomerRequestModel;
import com.viktorkuts.customers.presentationlayer.models.CustomerResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CustomerResponseMapper customerResponseMapper;
    private CustomerRequestMapper customerRequestMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerResponseMapper customerResponseMapper, CustomerRequestMapper customerRequestMapper) {
        this.customerRepository = customerRepository;
        this.customerResponseMapper = customerResponseMapper;
        this.customerRequestMapper = customerRequestMapper;
    }

    @Override
    public List<CustomerResponseModel> getCustomers() {
        return customerResponseMapper.entitiesToModels(customerRepository.findAll());
    }
    @Override
    public CustomerResponseModel getCustomer(String customerId) {
        Customer customer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId);
        return customerResponseMapper.entityToModel(customer);
    }

    @Override
    public CustomerResponseModel addCustomer(CustomerRequestModel requestModel) {
        Customer newCustomer = customerRequestMapper.modelToEntity(requestModel, new CustomerIdentifier());
        customerRepository.save(newCustomer);
        return customerResponseMapper.entityToModel(customerRepository.save(newCustomer));
    }

    @Override
    public CustomerResponseModel editCustomer(String customerId, CustomerRequestModel requestModel) {
        Customer foundCustomer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId);
        Customer updatedCustomer = customerRequestMapper.modelToEntity(requestModel, foundCustomer.getCustomerIdentifier());
        customerRepository.save(updatedCustomer);
        return customerResponseMapper.entityToModel(updatedCustomer);
    }
    @Override
    public void deleteCustomer(String customerId) {
        Customer foundCustomer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId);
        customerRepository.delete(foundCustomer);
    }
}
