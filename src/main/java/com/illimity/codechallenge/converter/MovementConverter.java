package com.illimity.codechallenge.converter;

import com.illimity.codechallenge.model.Movement;
import com.illimity.codechallenge.model.MovementInputModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MovementConverter {

    public Movement toMovement(MovementInputModel movementInputModel){
        Movement movement = new Movement();

        movement.setMovementId(UUID.randomUUID());
        movement.setCustomerId(movementInputModel.getCustomerId());
        movement.setDescription(movementInputModel.getDescription());
        movement.setAmount(movementInputModel.getAmount());
        movement.setDate(LocalDateTime.now());
        movement.setCurrency(movementInputModel.getCurrency());

        return movement;
    }

}
