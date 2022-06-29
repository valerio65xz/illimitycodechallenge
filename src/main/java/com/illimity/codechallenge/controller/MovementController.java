package com.illimity.codechallenge.controller;

import com.illimity.codechallenge.model.Movement;
import com.illimity.codechallenge.model.MovementInputModel;
import com.illimity.codechallenge.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movements")
public class MovementController {

    private final MovementService movementService;

    @Autowired
    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping
    public ResponseEntity<Movement> createMovement(@RequestBody @Valid MovementInputModel movementInputModel){
        Movement movement = movementService.createMovement(movementInputModel);
        return ResponseEntity.ok(movement);
    }

    @GetMapping("{movementId:[0-9a-f]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}}")
    public ResponseEntity<Movement> loadMovement(@PathVariable("movementId") UUID movementId) {
        Movement movement = movementService.findById(movementId);
        return ResponseEntity.ok(movement);
    }

    @GetMapping
    public ResponseEntity<List<Movement>> loadMovements() {
        List<Movement> movements = movementService.findAllMovements();
        return ResponseEntity.ok(movements);
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