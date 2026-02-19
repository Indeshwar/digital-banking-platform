package com.bank.customer.exception;

public class DublicateCustomerException extends RuntimeException{
    public DublicateCustomerException(String message){
        super(message);
    }
}
