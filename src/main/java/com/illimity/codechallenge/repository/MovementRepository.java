package com.illimity.codechallenge.repository;

import com.illimity.codechallenge.model.Movement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface MovementRepository extends MongoRepository<Movement, UUID> {

    List<Movement> findAllByCustomerId(UUID customerId, Pageable pageable);

}