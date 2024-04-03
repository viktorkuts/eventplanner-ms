package com.viktorkuts.customers.datalayer;

import com.viktorkuts.customers.presentationlayer.models.CustomerRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerRepositoryIntegrationTest {
    @Autowired
    CustomerRepository customerRepository;
    Customer presavedCustomer;
    @BeforeEach
    public void setup(){
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.HOME, "123-456-7890"));
        presavedCustomer = new Customer(
                new CustomerIdentifier(),
                CustomerType.PERFORMER,
                "John",
                "Doe",
                "john.doe@example.com",
                new Address("123 Main St", "Anytown", "NY", "US", "12345"),
                phoneNumbers
        );
        presavedCustomer = customerRepository.save(presavedCustomer);
    }

    @Test
    public void saveNewCustomer_shouldSucceed(){
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.HOME, "123-456-7890"));
        Customer newCustomer = new Customer(
                new CustomerIdentifier(),
                CustomerType.PERFORMER,
                "Quandale",
                "Doe",
                "john.doe@example.com",
                new Address("123 Main St", "Anytown", "NY", "US", "12345"),
                phoneNumbers
        );
        Customer savedCustomer = customerRepository.save(newCustomer);
        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
        assertEquals(newCustomer.getCustomerIdentifier().getCustomerId(), savedCustomer.getCustomerIdentifier().getCustomerId());
        assertEquals(newCustomer.getFirstName(), savedCustomer.getFirstName());
    }

    @Test
    public void updateCustomer_shouldSucceed(){
        presavedCustomer.setFirstName("Quandingdong");
        Customer updatedCustomer = customerRepository.save(presavedCustomer);
        assertNotNull(updatedCustomer);
        assertEquals(presavedCustomer.getId(), updatedCustomer.getId());
        assertEquals(presavedCustomer.getFirstName(), updatedCustomer.getFirstName());
    }

    @Test
    public void findCustomer_shouldSucceed(){
        Customer foundCustomer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(presavedCustomer.getCustomerIdentifier().getCustomerId());

        assertNotNull(foundCustomer);
        assertEquals(presavedCustomer.getId(), foundCustomer.getId());
    }

    @Test
    public void deleteCustomer_shouldSucceed(){
        Integer id = presavedCustomer.getId();
        String customerId = presavedCustomer.getCustomerIdentifier().getCustomerId();
        customerRepository.delete(presavedCustomer);
        assertNull(customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId));
    }

    @Test
    public void findCustomer_shouldFail(){
        Customer foundCustomer = customerRepository.findCustomerByCustomerIdentifier_CustomerId("idonotexist");
        assertNull(foundCustomer);
    }
}
