package com.illimity.codechallenge.service;

import com.illimity.codechallenge.converter.CustomerConverter;
import com.illimity.codechallenge.exception.ResponseErrorEnum;
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

    private final CustomerRepository customerRepository;

    private final CustomerConverter customerConverter;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerConverter customerConverter, PasswordEncoder passwordEncoder){
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer createCustomer(CustomerInputModel customerInputModel){
        Customer customer = customerConverter.toCustomer(customerInputModel);

        if (customerRepository.findByUsername(customer.getUsername()) != null){
            throw new ResponseException(ResponseErrorEnum.USER_ALREADY_EXISTS);
        }

        return customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer findById(UUID id){
        return customerRepository.findById(id)
                .orElse(null);
    }

    public void deleteCustomer(UUID id){
        customerRepository.deleteById(id);
    }

    public void deleteCustomers(){
        customerRepository.deleteAll();
    }

    public CustomerOutputModel login(String username, String rawPassword){
        Customer customer = customerRepository.findByUsername(username);

        if (customer == null){
            throw new ResponseException(ResponseErrorEnum.CUSTOMER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(rawPassword, customer.getEncodedPassword())){
            throw new ResponseException(ResponseErrorEnum.UNAUTHORIZED);
        }
        if (Status.ACTIVE != customer.getStatus()){
            throw new ResponseException(ResponseErrorEnum.STATUS_NOT_VALID);
        }

        customer.setLastModifiedDate(LocalDateTime.now());
        customer.setLastLoginDate(LocalDateTime.now());

        Customer savedCustomer = customerRepository.save(customer);
        return customerConverter.toCustomerOutputModel(savedCustomer);
    }

}