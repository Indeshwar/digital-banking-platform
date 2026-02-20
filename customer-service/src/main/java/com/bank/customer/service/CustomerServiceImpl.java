package com.bank.customer.service;

import com.bank.customer.dto.AddressDto;
import com.bank.customer.dto.CreateCustomerRequest;
import com.bank.customer.dto.CustomerResponse;
import com.bank.customer.dto.UpdateCustomerRequest;
import com.bank.customer.entity.Address;
import com.bank.customer.entity.Customer;
import com.bank.customer.enums.KycStatus;
import com.bank.customer.exception.CustomerNotFoundException;
import com.bank.customer.exception.DublicateCustomerException;
import com.bank.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request){
        //Check dublicate  email
        Optional<Customer> existingCustomer = customerRepository.findByEmail(request.getEmail());
        if(!existingCustomer.isEmpty()){
            throw new DublicateCustomerException("Customer contains" + request.getEmail() + " already exist");
        }

        //Map DTO to Entity
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .kycStatus(KycStatus.PENDING)
                .build();

        //Map Address if provided
        if(request.getAddress() != null){
            Address address = Address.builder()
                    .addressLine1(request.getAddress().getAddressLine1())
                    .city(request.getAddress().getCity())
                    .state(request.getAddress().getState())
                    .zip(request.getAddress().getZip())
                    .country(request.getAddress().getCountry())
                    .build();
            customer.setAddresses(List.of(address));
        }

        //Save customer in database
        Customer savedCustomer = customerRepository.save(customer);

        //Map Customer to CustomerResponse Dto
        CustomerResponse customerResponse = mapCustomerToCustomerResponse(savedCustomer);


        //Map Address to AddressDto
        if(savedCustomer.getAddresses() != null){
            Address address = savedCustomer.getAddresses().get(0);
            AddressDto addressDto = addressToAddressDto(address);
            customerResponse.setAddress(addressDto);
        }

        return  customerResponse;
    }

    private AddressDto addressToAddressDto(Address address){
        return AddressDto.builder()
                .addressLine1(address.getAddressLine1())
                .city(address.getCity())
                .state(address.getState())
                .zip(address.getZip())
                .country(address.getCountry())
                .build();


    }

    private CustomerResponse mapCustomerToCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .kycStatus(customer.getKycStatus())
                .createdAt(customer.getCreatedAt())
                .build();
    }

    @Override
    public CustomerResponse getCustomerById(UUID id) {

        Optional<Customer> existingCustomer = customerRepository.getCustomerById(id);

        //Check the customer id
        if(existingCustomer.isEmpty()){
            throw new CustomerNotFoundException("Customer contains id : " + id + " not found");
        }

        Customer customer = existingCustomer.get();

        //Map Customer to CustomerResponse
        CustomerResponse response = mapCustomerToCustomerResponse(customer);

        //Map Address to AddressDto
        if(customer.getAddresses() != null){
            AddressDto address = addressToAddressDto(customer.getAddresses().get(0));
            //set address int response
            response.setAddress(address);
        }


        return response;
    }


    @Override
    public CustomerResponse updateCustomer(UpdateCustomerRequest request, UUID id) {
        Optional<Customer> existingCustomer = customerRepository.getCustomerById(id);

        //Check customer id
        if(existingCustomer.isEmpty()){
            throw new CustomerNotFoundException("Customer contains id " + id +  "not found");
        }

        Customer updateCustomer = existingCustomer.get();

        //Set the update data
        if(!request.getFirstName().isEmpty()){
            updateCustomer.setFirstName(request.getFirstName());
        }

        if(!request.getLastName().isEmpty()){
            updateCustomer.setLastName(request.getLastName());
        }

        if(!request.getEmail().isEmpty()){
            updateCustomer.setEmail(request.getEmail());
        }

        if(!request.getPhone().isEmpty()){
            updateCustomer.setPhone(request.getPhone());
        }



        //save the update data in database
        Customer customer = customerRepository.save(updateCustomer);

        //Map Customer to CustomerResponse
        CustomerResponse response = mapCustomerToCustomerResponse(customer);

        //Map Address to AddressDto
        if(customer.getAddresses() != null){
            AddressDto address = addressToAddressDto(customer.getAddresses().get(0));
            //set address int response
            response.setAddress(address);
        }

        return response;
    }
}
