package com.viktorkuts.events.domainclientlayer.customers;

import lombok.*;

@Data
@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerModel {
    String customerId;
    CustomerType customerType;
    String firstName;
    String lastName;
}
