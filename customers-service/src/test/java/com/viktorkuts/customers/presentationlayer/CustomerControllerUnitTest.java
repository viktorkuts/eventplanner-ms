package com.viktorkuts.customers.presentationlayer;

import com.viktorkuts.customers.datalayer.Address;
import com.viktorkuts.customers.datalayer.PhoneNumber;
import com.viktorkuts.customers.datalayer.PhoneType;
import com.viktorkuts.customers.logiclayer.CustomerService;
import com.viktorkuts.customers.logiclayer.CustomerServiceImpl;
import com.viktorkuts.customers.presentationlayer.models.CustomerRequestModel;
import com.viktorkuts.customers.presentationlayer.models.CustomerResponseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CustomerController.class)
public class CustomerControllerUnitTest {
    private final String VALIDCUSTOMER = "b145e3f9-738d-4fda-a60c-c6a4c91a183c";
    private final String I = "hello";

    @Autowired
    CustomerController customerController;

    @MockBean
    private CustomerServiceImpl customerService;

    @Test
    public void whenNoCustomerExists_ThenReturnEmptyList(){
        when(customerService.getCustomers()).thenReturn(Collections.emptyList());

        ResponseEntity<List<CustomerResponseModel>> responseEntity = customerController.getAll();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(customerService, times(1)).getCustomers();
    }

    @Test
    public void whenCustomerExists_ThenReturnCustomer(){
        CustomerRequestModel requestModel = buildCustomerRequestModel();
        CustomerResponseModel responseModel = buildCustomerResponseModel();

        when(customerService.addCustomer(requestModel)).thenReturn(responseModel);

        ResponseEntity<CustomerResponseModel> responseEntity = customerController.addCustomer(requestModel);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseModel, responseEntity.getBody());
        verify(customerService, times(1)).addCustomer(requestModel);

    }

    private CustomerRequestModel buildCustomerRequestModel(){
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.HOME, "1234567890"));

        return CustomerRequestModel.builder()
                .firstName("John")
                .lastName("Doe")
                .emailAddress("j.doe@doe.com")
                .address(new Address("123 street", "a city", "qc", "canada", "j1vh2k"))
                .phoneNumbers(phoneNumbers)
                .build();
    }

    private CustomerResponseModel buildCustomerResponseModel(){
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.HOME, "1234567890"));

        return CustomerResponseModel.builder()
                .customerId(VALIDCUSTOMER)
                .firstName("John")
                .lastName("Doe")
                .emailAddress("j.doe@doe.com")
                .address(new Address("123 street", "a city", "qc", "canada", "j1vh2k"))
                .phoneNumbers(phoneNumbers)
                .build();
    }
}
