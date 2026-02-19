package com.bank.customer.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private String addressLine1;

    private String city;

    private String state;

    private String zip;

    private String country;
}
