package com.bank.customer.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCustomerRequest {
    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private AddressDto address;
}
