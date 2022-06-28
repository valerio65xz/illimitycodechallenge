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
    public Customer loadCustomer(@PathVariable("customerId") UUID customerId) {
        return customerService.findById(customerId);
    }

    @GetMapping
    public List<Customer> loadCustomers() {
        return customerService.findAllCustomers();
    }

    @DeleteMapping("{customerId:[0-9a-f]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}}")
    public void deleteCustomer(@PathVariable("customerId") UUID customerId){
        customerService.deleteCustomer(customerId);
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

}