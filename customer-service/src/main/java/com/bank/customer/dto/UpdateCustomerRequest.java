package com.bank.customer.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCustomerRequest {
    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private AddressDto address;

}
