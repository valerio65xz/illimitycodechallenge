package com.illimity.codechallenge.controller;

import com.illimity.codechallenge.model.CustomerInputModel;
import com.illimity.codechallenge.model.MovementInputModel;
import com.illimity.codechallenge.model.Status;
import com.illimity.codechallenge.service.CustomerService;
import com.illimity.codechallenge.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/init")
public class InitController {

    private final CustomerService customerService;
    private final MovementService movementService;

    @Autowired
    public InitController(CustomerService customerService, MovementService movementService) {
        this.customerService = customerService;
        this.movementService = movementService;
    }

    @GetMapping
    public ResponseEntity<Void> init(){
        CustomerInputModel marioRossi = new CustomerInputModel(
                "Mario",
                "Rossi",
                "MarioRossi",
                "mariorossi",
                "codiceFiscaleMarioRossi",
                "mario.rossi@email.it",
                "1234567890",
                Status.ACTIVE
        );
        CustomerInputModel luigiVerdi = new CustomerInputModel(
                "Luigi",
                "Verdi",
                "LuigiVerdi",
                "luigiverdi",
                "codiceFiscaleLuigiVerdi",
                "luigi.verdi@email.it",
                "1234567890",
                Status.BLOCKED
        );

        customerService.createCustomer(marioRossi);
        customerService.createCustomer(luigiVerdi);

        UUID customerId = UUID.randomUUID();
        List<MovementInputModel> movements = IntStream.range(1,12)
                .mapToObj(i -> new MovementInputModel(
                        customerId,
                        "movementDescription",
                        BigDecimal.valueOf(i),
                        "EUR"
                ))
                .collect(Collectors.toList());

        movements.forEach(movementService::createMovement);

        return ResponseEntity.ok(null);
    }

}