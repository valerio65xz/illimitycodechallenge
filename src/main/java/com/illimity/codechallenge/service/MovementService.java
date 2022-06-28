package com.illimity.codechallenge.service;

import com.illimity.codechallenge.converter.MovementConverter;
import com.illimity.codechallenge.model.Movement;
import com.illimity.codechallenge.model.MovementInputModel;
import com.illimity.codechallenge.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovementService {


    private final MovementRepository repository;
    private final MovementConverter movementConverter;

    @Autowired
    public MovementService(MovementRepository repository, MovementConverter movementConverter) {
        this.repository = repository;
        this.movementConverter = movementConverter;
    }

    public Movement createMovement(MovementInputModel movementInputModel){
        Movement movement = movementConverter.toMovement(movementInputModel);

        return repository.save(movement);
    }

    public List<Movement> findAllMovements(){
        return repository.findAll();
    }

    public Movement findById(UUID id){
        return repository.findById(id)
                .orElse(null);
    }

    public void deleteMovement(UUID id){
        repository.deleteById(id);
    }

    public void deleteMovements(){
        repository.deleteAll();
    }

    public List<Movement> findAllMovementsByCustomerId(UUID customerId, Pageable pageable){
        return repository.findAllByCustomerId(customerId, pageable);
    }

}