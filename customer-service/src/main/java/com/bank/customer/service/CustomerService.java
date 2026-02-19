package com.bank.customer.service;

import com.bank.customer.dto.CreateCustomerRequest;
import com.bank.customer.dto.CustomerResponse;
import com.bank.customer.dto.UpdateCustomerRequest;

import java.util.UUID;

public interface CustomerService {
    public CustomerResponse createCustomer(CreateCustomerRequest request);

    public CustomerResponse getCustomerById(UUID id);

    public CustomerResponse updateCustomer(UpdateCustomerRequest request, UUID id);

}
