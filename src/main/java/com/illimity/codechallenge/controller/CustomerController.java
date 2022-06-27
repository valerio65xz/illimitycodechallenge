package com.illimity.codechallenge.controller;

import com.illimity.codechallenge.exception.ResponseException;
import com.illimity.codechallenge.model.*;
import com.illimity.codechallenge.repository.CustomerRepository;
import com.illimity.codechallenge.response.ErrorResponse;
import com.illimity.codechallenge.service.CustomerService;
import com.illimity.codechallenge.service.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody @Valid CustomerInputModel customerInputModel){
        return customerService.createCustomer(customerInputModel);
    }

    @GetMapping("{customerId:[0-9a-f]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}}")
    public CustomerOutputModel loadCustomer(@PathVariable("customerId") UUID customerId) {
        return customerService.findById(customerId);
    }

    @GetMapping
    public List<CustomerOutputModel> loadCustomers() {
        return customerService.findAllCustomers();
    }

    @DeleteMapping("{customerId:[0-9a-f]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}}")
    public void deleteCustomer(@PathVariable("customerId") UUID customerId){
        customerService.deleteCustomers();
    }

    @DeleteMapping
    public void deleteCustomers(){
        customerService.deleteCustomers();
    }

    @PostMapping("login")
    public ResponseEntity<CustomerOutputModel> login(@RequestBody @Valid LoginInputModel loginInputModel){
        CustomerOutputModel customerOutputModel = customerService.login(loginInputModel.getUsername(), loginInputModel.getPassword());
        return ResponseEntity.ok(customerOutputModel);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BindException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getValidationErrorMessage)
                .collect(Collectors.toList());

        return createErrorResponseEntity(HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException ex) {
        return createErrorResponseEntity(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ResponseException ex) {
        return createErrorResponseEntity(
                ex.getError().getHttpStatus(),
                Collections.singletonList(ex.getError().getMessage()));
    }

    private ResponseEntity<ErrorResponse> createErrorResponseEntity(int status, List<String> message){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

    private String getValidationErrorMessage(ObjectError objectError){
        String field = Optional.ofNullable(objectError)
                .map(ObjectError::getCodes)
                .map(codes -> codes[1].substring(codes[3].length() + 1))
                .map(substring -> substring.concat(" "))
                .orElse("");

        return Optional.ofNullable(objectError)
                .map(ObjectError::getDefaultMessage)
                .map(field::concat)
                .orElse("");
    }

}
