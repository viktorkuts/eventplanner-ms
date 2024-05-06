package com.viktorkuts.customers.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Customer findCustomerByCustomerIdentifier_CustomerId(String customerId);
}
