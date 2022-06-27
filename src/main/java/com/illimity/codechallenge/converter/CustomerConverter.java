package com.illimity.codechallenge.converter;

import com.illimity.codechallenge.model.Customer;
import com.illimity.codechallenge.model.CustomerInputModel;
import com.illimity.codechallenge.model.CustomerOutputModel;
import com.illimity.codechallenge.service.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CustomerConverter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer toCustomer(CustomerInputModel customerInputModel){
        Customer customer = new Customer();

        customer.setCustomerId(UUID.randomUUID());
        customer.setName(customerInputModel.getName());
        customer.setSurname(customerInputModel.getSurname());
        customer.setUsername(customerInputModel.getUsername());
        customer.setEncodedPassword(passwordEncoder.encode(customerInputModel.getPassword()));
        customer.setFiscalCode(customerInputModel.getFiscalCode());
        customer.setCreatedDate(LocalDateTime.now());
        customer.setStatus(customerInputModel.getStatus());
        customer.setEmail(customerInputModel.getEmail());
        customer.setPhoneNumber(customerInputModel.getPhoneNumber());

        return customer;
    }

    public CustomerOutputModel toCustomerOutputModel(Customer customer){
        CustomerOutputModel customerOutputModel = new CustomerOutputModel();

        customerOutputModel.setName(customer.getName());
        customerOutputModel.setSurname(customer.getSurname());
        customerOutputModel.setFiscalCode(customer.getFiscalCode());
        customerOutputModel.setEmail(customer.getEmail());
        customerOutputModel.setPhoneNumber(customer.getPhoneNumber());
        customerOutputModel.setStatus(customer.getStatus());

        return  customerOutputModel;
    }

}
