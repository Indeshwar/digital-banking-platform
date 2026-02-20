package com.bank.customer.controller;

import com.bank.customer.dto.CreateCustomerRequest;
import com.bank.customer.dto.CustomerResponse;
import com.bank.customer.dto.UpdateCustomerRequest;
import com.bank.customer.service.CustomerService;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("customers")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid  @RequestBody CreateCustomerRequest request){
        CustomerResponse response = customerService.createCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable UUID id){
        CustomerResponse response = customerService.getCustomerById(id);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("customers/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable UUID id, @Valid @RequestBody UpdateCustomerRequest request){
        CustomerResponse response = customerService.updateCustomer(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
