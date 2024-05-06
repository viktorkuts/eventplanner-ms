package com.viktorkuts.customers.datalayer;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class Address {
	@Column(name = "streetaddress")
	private String streetAddress;

	@Column(name = "city")
	private String city;

	@Column(name = "province")
	private String province;

	@Column(name = "country")
	private String country;

	@Column(name = "postalcode")
	private String postalcode;

	public Address(
			@NotNull String streetAddress,
			@NotNull String city,
			@NotNull String province,
			@NotNull String country,
			@NotNull String postalCode
	) {
		Objects.requireNonNull(this.streetAddress = streetAddress);
		Objects.requireNonNull(this.city = city);
		Objects.requireNonNull(this.province = province);
		Objects.requireNonNull(this.country = country);
		Objects.requireNonNull(this.postalcode = postalCode);
	}
}
