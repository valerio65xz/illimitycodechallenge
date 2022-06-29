package com.illimity.codechallenge.controller;

import com.illimity.codechallenge.model.Customer;
import com.illimity.codechallenge.model.CustomerInputModel;
import com.illimity.codechallenge.model.CustomerOutputModel;
import com.illimity.codechallenge.model.LoginInputModel;
import com.illimity.codechallenge.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid CustomerInputModel customerInputModel){
        Customer customer = customerService.createCustomer(customerInputModel);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("{customerId:[0-9a-f]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}}")
    public ResponseEntity<Customer> loadCustomer(@PathVariable("customerId") UUID customerId) {
        Customer customer = customerService.findById(customerId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> loadCustomers() {
        List<Customer> customers = customerService.findAllCustomers();
        return ResponseEntity.ok(customers);
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