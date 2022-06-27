package com.illimity.codechallenge.service;

import com.illimity.codechallenge.controller.MovementController;
import com.illimity.codechallenge.converter.MovementConverter;
import com.illimity.codechallenge.exception.ResponseError;
import com.illimity.codechallenge.exception.ResponseException;
import com.illimity.codechallenge.model.*;
import com.illimity.codechallenge.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovementService {

    @Autowired
    private MovementRepository repository;

    @Autowired
    private MovementConverter movementConverter;

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

    public List<Movement> findAllMovementsOrderedByDate(UUID customerId, Pageable pageable){
        Pageable pageable2 = PageRequest.of(0, 10, Sort.by("date").descending());

        return repository.findAllByCustomerId(customerId, pageable);
    }

}
