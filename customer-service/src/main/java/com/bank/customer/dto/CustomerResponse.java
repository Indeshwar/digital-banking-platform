package com.bank.customer.dto;

import com.bank.customer.enums.KycStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private KycStatus kycStatus;

    private AddressDto address;

    private LocalDateTime createdAt;

}
