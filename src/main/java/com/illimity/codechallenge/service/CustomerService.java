package com.illimity.codechallenge.service;

import com.illimity.codechallenge.model.Customer;
import com.illimity.codechallenge.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Customer createCustomer(Customer customer){
        customer.setCustomerId(UUID.randomUUID());
        return repository.save(customer);
    }

    public List<Customer> findAllCustomers(){
        return repository.findAll();
    }

    public Customer findById(UUID id){
        return repository.findById(id)
                .orElse(null);
    }

    public void deleteCustomers(){
        repository.deleteAll();
    }

}
