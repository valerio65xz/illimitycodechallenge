package com.illimity.codechallenge.repository;

import com.illimity.codechallenge.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends MongoRepository<Customer, UUID> {

    Customer findByUsername(String username);

}