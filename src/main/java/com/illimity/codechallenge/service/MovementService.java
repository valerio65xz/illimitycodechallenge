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


    private final MovementRepository movementRepository;
    private final MovementConverter movementConverter;

    @Autowired
    public MovementService(MovementRepository movementRepository, MovementConverter movementConverter) {
        this.movementRepository = movementRepository;
        this.movementConverter = movementConverter;
    }

    public Movement createMovement(MovementInputModel movementInputModel){
        Movement movement = movementConverter.toMovement(movementInputModel);

        return movementRepository.save(movement);
    }

    public List<Movement> findAllMovements(){
        return movementRepository.findAll();
    }

    public Movement findById(UUID id){
        return movementRepository.findById(id)
                .orElse(null);
    }

    public void deleteMovement(UUID id){
        movementRepository.deleteById(id);
    }

    public void deleteMovements(){
        movementRepository.deleteAll();
    }

    public List<Movement> findAllMovementsByCustomerId(UUID customerId, Pageable pageable){
        return movementRepository.findAllByCustomerId(customerId, pageable);
    }

}