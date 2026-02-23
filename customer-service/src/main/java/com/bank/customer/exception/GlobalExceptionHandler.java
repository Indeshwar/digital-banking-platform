package com.bank.customer.exception;

import com.bank.customer.dto.DtoErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    //It handles Validation exception to object
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DtoErrorResponse>> handleValidationException( MethodArgumentNotValidException exception){
        List< DtoErrorResponse> errors = exception.getBindingResult().getFieldErrors().stream()
                .map((error)-> new DtoErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.name(),
                        error.getDefaultMessage()

                )).collect(Collectors.toList());
        return  new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //It handles Parameter constrain exception
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DtoErrorResponse> handleParameterConstrain(ConstraintViolationException e){
        DtoErrorResponse error = new DtoErrorResponse(
              LocalDateTime.now(),
              HttpStatus.BAD_REQUEST.value(),
              HttpStatus.BAD_REQUEST.name(),
                e.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //It handles Generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DtoErrorResponse> handleGenericException(Exception e){
        DtoErrorResponse error = new DtoErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                "Unexpected Error message occurred. Please try again later"
        );

        return  new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Handle Business exception
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<DtoErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException e){
        DtoErrorResponse error = new DtoErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                e.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DublicateCustomerException.class)
    public ResponseEntity<DtoErrorResponse> handleDublicateCustomerException(DublicateCustomerException e){
        DtoErrorResponse error = new DtoErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                e.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}
