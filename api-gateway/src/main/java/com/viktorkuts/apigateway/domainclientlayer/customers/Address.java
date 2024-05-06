package com.viktorkuts.apigateway.domainclientlayer.customers;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
public class Address {
    private String streetAddress;
    private String city;
    private String province;
    private String country;
    private String postalcode;

    public Address(
            @NotNull String streetAddress,
            @NotNull String city,
            @NotNull String province,
            @NotNull String country,
            @NotNull String postalcode
    ){
        Objects.requireNonNull(this.streetAddress = streetAddress);
        Objects.requireNonNull(this.city = city);
        Objects.requireNonNull(this.province = province);
        Objects.requireNonNull(this.country = country);
        Objects.requireNonNull(this.postalcode = postalcode);
    }
}
