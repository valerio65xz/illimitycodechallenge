package com.illimity.codechallenge.controller;

import com.illimity.codechallenge.model.Customer;
import com.illimity.codechallenge.repository.CustomerRepository;
import com.illimity.codechallenge.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    @GetMapping("{customerId:[0-9a-f]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}}")
    public Customer loadCustomer(@PathVariable("customerId") UUID customerId) {
        return customerService.findById(customerId);
    }

    @GetMapping
    public List<Customer> loadCustomers() {
        return customerService.findAllCustomers();
    }

    @DeleteMapping
    public void deleteCustomers(){
        customerService.deleteCustomers();
    }

}
