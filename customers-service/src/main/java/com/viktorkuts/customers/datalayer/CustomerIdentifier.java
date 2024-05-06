package com.viktorkuts.customers.datalayer;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class CustomerIdentifier {
	@Column(name = "customerid")
	private String customerId;

	public CustomerIdentifier() {
		this.customerId = UUID.randomUUID().toString();
	}
}
