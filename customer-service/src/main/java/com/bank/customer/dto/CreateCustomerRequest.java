package com.bank.customer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCustomerRequest {
    @NotBlank(message = "Please enter first name")
    private String firstName;

    @NotBlank(message = "Please enter last name")
    private String lastName;

    @Email(message = "Please enter valid email")
    private String email;

    private static final String PHONE_REGEX = "^(\\([0-9]{3}\\)|[0-9]{3}-)[0-9]{3}-[0-9]{4}$";

    @Pattern(regexp = PHONE_REGEX, message = "Invalid phone number format. Expected formats: 123-456-7890 or (123)456-7890")
    private String phone;

    @Valid
    private AddressDto address;
}
