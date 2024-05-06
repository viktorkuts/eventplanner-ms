package com.viktorkuts.customers.presentationlayer;

import com.viktorkuts.customers.datalayer.*;
import com.viktorkuts.customers.presentationlayer.models.CustomerRequestModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:customers-db"})
@Sql({"/schema-h2.sql", "/data-h2.sql"})
public class CustomerControllerIntegrationTest {
    private final String BASE_URL = "/api/v1/customers";
    private final String VALID_CUSTOMER_ID = "6d7e2586-d3e1-4153-9666-bb17ed4ca50e";
    private final String VALID_NUMBERED_POSTAL_CODE = "18273";
    private final String VALID_STRING_POSTAL_CODE = "J2H 1H7";
    private final String INVALID_NUMBERED_POSTAL_CODE = "123456";
    private final String INVALID_STRING_POSTAL_CODE = "J2H 11H";

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    WebTestClient webTestClient;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    public void whenCustomersExist_thenGetAllCustomers() {
        webTestClient.get()
                .uri(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(10);
    }

    @Test
    public void whenCustomerExists_thenGetCustomerById() {
        webTestClient.get()
                .uri(BASE_URL + "/" + VALID_CUSTOMER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.customerId").isEqualTo(VALID_CUSTOMER_ID);
    }

    @Test
    public void whenUpdateCustomer_thenReturnUpdatedCustomer() {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.HOME, "123-456-7890"));
        CustomerRequestModel customerRequestModel = new CustomerRequestModel(CustomerType.PERFORMER, "John", "Doe", "john.doe@example.com", new Address("123 Main St", "Anytown", "NY", "US", VALID_STRING_POSTAL_CODE), phoneNumbers);
        webTestClient.put()
                .uri(BASE_URL + "/" + VALID_CUSTOMER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customerRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.customerId").isEqualTo(VALID_CUSTOMER_ID)
                .jsonPath("$.customerType").isEqualTo("PERFORMER");
    }

    @Test
    public void whenDeleteCustomer_thenCustomerIsDeleted() {
        assertEquals(10, customerRepository.count());
        webTestClient.delete()
                .uri(BASE_URL + "/" + VALID_CUSTOMER_ID)
                .exchange()
                .expectStatus().isNoContent();
        assertEquals(9, customerRepository.count());
        assertNull(customerRepository.findCustomerByCustomerIdentifier_CustomerId(VALID_CUSTOMER_ID));
    }

    @Test
    public void whenCreateCustomerWithInvalidNumberedPostalCode_thenBadRequest() {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.HOME, "123-456-7890"));
        CustomerRequestModel customerRequestModel = new CustomerRequestModel(CustomerType.PERFORMER, "John", "Doe", "john.doe@example.com", new Address("123 Main St", "Anytown", "NY", "US", INVALID_NUMBERED_POSTAL_CODE), phoneNumbers);
        webTestClient.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customerRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(422);
    }

    @Test
    public void whenCreateCustomerWithInvalidStringPostalCode_thenBadRequest() {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(PhoneType.HOME, "123-456-7890"));
        CustomerRequestModel customerRequestModel = new CustomerRequestModel(CustomerType.PERFORMER, "John", "Doe", "john.doe@example.com", new Address("123 Main St", "Anytown", "NY", "US", INVALID_STRING_POSTAL_CODE), phoneNumbers);
        webTestClient.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(customerRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(422);
    }
}
