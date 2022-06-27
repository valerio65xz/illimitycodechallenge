package com.illimity.codechallenge.repository;

import com.illimity.codechallenge.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends MongoRepository<Customer, UUID> {

    List<Customer> findByName(String name);

}
