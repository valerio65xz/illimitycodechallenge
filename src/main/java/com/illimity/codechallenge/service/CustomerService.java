package com.illimity.codechallenge.service;

import com.illimity.codechallenge.converter.CustomerConverter;
import com.illimity.codechallenge.exception.ResponseError;
import com.illimity.codechallenge.exception.ResponseException;
import com.illimity.codechallenge.model.Customer;
import com.illimity.codechallenge.model.CustomerInputModel;
import com.illimity.codechallenge.model.CustomerOutputModel;
import com.illimity.codechallenge.model.Status;
import com.illimity.codechallenge.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    private final CustomerConverter customerConverter;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository repository, CustomerConverter customerConverter, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.customerConverter = customerConverter;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer createCustomer(CustomerInputModel customerInputModel){
        Customer customer = customerConverter.toCustomer(customerInputModel);

        if (repository.findByUsername(customer.getUsername()) != null){
            throw new ResponseException(ResponseError.USER_ALREADY_EXISTS);
        }

        return repository.save(customer);
    }

    public List<Customer> findAllCustomers(){
        return repository.findAll();
    }

    public Customer findById(UUID id){
        return repository.findById(id)
                .orElse(null);
    }

    public void deleteCustomer(UUID id){
        repository.deleteById(id);
    }

    public void deleteCustomers(){
        repository.deleteAll();
    }

    public CustomerOutputModel login(String username, String rawPassword){
        Customer customer = repository.findByUsername(username);

        if (customer == null){
            throw new ResponseException(ResponseError.CUSTOMER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(rawPassword, customer.getEncodedPassword())){
            throw new ResponseException(ResponseError.UNAUTHORIZED);
        }
        if (Status.ACTIVE != customer.getStatus()){
            throw new ResponseException(ResponseError.STATUS_NOT_VALID);
        }

        customer.setLastModifiedDate(LocalDateTime.now());
        customer.setLastLoginDate(LocalDateTime.now());

        Customer savedCustomer = repository.save(customer);
        return customerConverter.toCustomerOutputModel(savedCustomer);
    }

}