package com.viktorkuts.apigateway.domainclientlayer.customers;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Data
@NoArgsConstructor
public class PhoneNumber {
    private PhoneType type;
    private String number;

    public PhoneNumber(@NotNull PhoneType type, @NotNull String number){
        Objects.requireNonNull(this.type = type);
        Objects.requireNonNull(this.number = number);
    }
}
