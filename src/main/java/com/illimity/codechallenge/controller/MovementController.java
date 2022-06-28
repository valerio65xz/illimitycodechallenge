package com.illimity.codechallenge.controller;

import com.illimity.codechallenge.exception.ResponseException;
import com.illimity.codechallenge.model.Movement;
import com.illimity.codechallenge.model.LoginInputModel;
import com.illimity.codechallenge.model.MovementInputModel;
import com.illimity.codechallenge.response.ErrorResponse;
import com.illimity.codechallenge.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movements")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @PostMapping
    public Movement createMovement(@RequestBody @Valid MovementInputModel movementInputModel){
        return movementService.createMovement(movementInputModel);
    }

    @GetMapping("{movementId:[0-9a-f]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}}")
    public Movement loadMovement(@PathVariable("movementId") UUID movementId) {
        return movementService.findById(movementId);
    }

    @GetMapping
    public List<Movement> loadMovements() {
        return movementService.findAllMovements();
    }

    @DeleteMapping("{movementId:[0-9a-f]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}}")
    public void deleteMovement(@PathVariable("movementId") UUID movementId){
        movementService.deleteMovement(movementId);
    }

    @DeleteMapping
    public void deleteMovements(){
        movementService.deleteMovements();
    }

    @GetMapping("paginated/{customerId:[0-9a-f]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}}")
    public ResponseEntity<List<Movement>> loadPaginatedMovements(@PathVariable("customerId") UUID customerId, Pageable pageable){
        List<Movement> movements = movementService.findAllMovementsByCustomerId(customerId, pageable);
        return ResponseEntity.ok(movements);
    }

}