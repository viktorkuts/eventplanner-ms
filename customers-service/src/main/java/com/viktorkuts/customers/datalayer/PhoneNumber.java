package com.viktorkuts.customers.datalayer;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class PhoneNumber {
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private PhoneType type;

	@Column(name = "number")
	private String number;

	public PhoneNumber(@NotNull PhoneType type, @NotNull String number){
		Objects.requireNonNull(this.type = type);
		Objects.requireNonNull(this.number = number);
	}
}
