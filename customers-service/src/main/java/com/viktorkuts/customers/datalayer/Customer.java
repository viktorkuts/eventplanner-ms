package com.viktorkuts.customers.datalayer;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Embedded
	private CustomerIdentifier customerIdentifier;

	@Column(name = "customertype")
	@Enumerated(EnumType.STRING)
	private CustomerType customerType;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "emailaddress")
	private String emailAddress;

	@Embedded
	private Address address;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "customer_phonenumbers", joinColumns = @JoinColumn(name = "custinternalid"))
	private List<PhoneNumber> phoneNumbers;



	public Customer(
			@NotNull CustomerIdentifier customerIdentifier,
			@NotNull CustomerType customerType,
			@NotNull String firstName,
			@NotNull String lastName,
			@NotNull String emailAddress,
			@NotNull Address address,
			@NotNull List<PhoneNumber> phoneNumbers
	) {
		Objects.requireNonNull(this.customerIdentifier = customerIdentifier);
		Objects.requireNonNull(this.customerType = customerType);
		Objects.requireNonNull(this.firstName = firstName);
		Objects.requireNonNull(this.lastName = lastName);
		Objects.requireNonNull(this.emailAddress = emailAddress);
		Objects.requireNonNull(this.address = address);
		Objects.requireNonNull(this.phoneNumbers = phoneNumbers);
	}
}
