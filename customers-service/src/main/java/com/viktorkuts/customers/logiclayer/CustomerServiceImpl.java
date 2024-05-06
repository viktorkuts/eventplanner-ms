package com.viktorkuts.customers.logiclayer;

import com.viktorkuts.customers.datalayer.Customer;
import com.viktorkuts.customers.datalayer.CustomerIdentifier;
import com.viktorkuts.customers.datalayer.CustomerRepository;
import com.viktorkuts.customers.mapperlayer.CustomerRequestMapper;
import com.viktorkuts.customers.mapperlayer.CustomerResponseMapper;
import com.viktorkuts.customers.presentationlayer.models.CustomerRequestModel;
import com.viktorkuts.customers.presentationlayer.models.CustomerResponseModel;
import com.viktorkuts.customers.utils.exceptions.InUseException;
import com.viktorkuts.customers.utils.exceptions.InvalidPostalCodeException;
import com.viktorkuts.customers.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CustomerResponseMapper customerResponseMapper;
    private CustomerRequestMapper customerRequestMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerResponseMapper customerResponseMapper, CustomerRequestMapper customerRequestMapper) {
        this.customerRepository = customerRepository;
        this.customerResponseMapper = customerResponseMapper;
        this.customerRequestMapper = customerRequestMapper;
    }

    private void validatePostalCode(String postal){
        if(postal != null){
            boolean valid = false;
            valid = postal.matches("\\d{5}") || postal.matches("^[A-Za-z]\\d[A-Za-z] ?\\d[A-Za-z]\\d$");
            if(!valid){
                throw new InvalidPostalCodeException("Invalid postal code, can only be in format 12345 or A1A 1A1 (US and Canada format)");
            }
        }
    }

    @Override
    public List<CustomerResponseModel> getCustomers() {
        return customerResponseMapper.entitiesToModels(customerRepository.findAll());
    }
    @Override
    public CustomerResponseModel getCustomer(String customerId) {
        Customer foundCustomer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId);
        if(foundCustomer == null){
            throw new NotFoundException("Unknown customerId: " + customerId);
        }
        return customerResponseMapper.entityToModel(foundCustomer);
    }

    @Override
    public CustomerResponseModel addCustomer(CustomerRequestModel requestModel) {
        Customer newCustomer = customerRequestMapper.modelToEntity(requestModel, new CustomerIdentifier());
        String postal = newCustomer.getAddress().getPostalcode();
        log.debug("HELLO!");
        log.debug(requestModel.toString());
        System.out.println("HELLO");
        System.out.println(requestModel.toString());
        validatePostalCode(postal);
        customerRepository.save(newCustomer);
        return customerResponseMapper.entityToModel(customerRepository.save(newCustomer));
    }
    @Override
    public CustomerResponseModel editCustomer(String customerId, CustomerRequestModel requestModel) {
        Customer foundCustomer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId);
        if(foundCustomer == null){
            throw new NotFoundException("Unknown customerId: " + customerId);
        }
        Customer updatedCustomer = customerRequestMapper.modelToEntity(requestModel, foundCustomer.getCustomerIdentifier());
        validatePostalCode(updatedCustomer.getAddress().getPostalcode());
        updatedCustomer.setId(foundCustomer.getId());
        customerRepository.save(updatedCustomer);
        return customerResponseMapper.entityToModel(updatedCustomer);
    }
    @Override
    public void deleteCustomer(String customerId) {
        Customer foundCustomer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId);
        if(foundCustomer == null){
            throw new NotFoundException("Unknown customerId: " + customerId);
        }
        try {
            customerRepository.delete(foundCustomer);
        } catch (DataIntegrityViolationException e) {
            throw new InUseException("User is in use by other resource!");
        }
    }
}
