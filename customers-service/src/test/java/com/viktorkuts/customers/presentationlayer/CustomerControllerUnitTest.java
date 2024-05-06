package com.viktorkuts.customers.presentationlayer;

import com.viktorkuts.customers.logiclayer.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CustomerController.class)
public class CustomerControllerUnitTest {
    private final String validCustomer = "b145e3f9-738d-4fda-a60c-c6a4c91a183c";
    private final String nonvalidCustomer = "hello";

    @Autowired
    CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    @Test
    public void whenCustomerExists_thenGetCustomerById() {
        when(customerService.getCustomerById(validCustomer)).thenReturn(null);
    }
}
