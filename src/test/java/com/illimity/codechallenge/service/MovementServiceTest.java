package com.illimity.codechallenge.service;

import com.illimity.codechallenge.BaseUnitTest;
import com.illimity.codechallenge.converter.MovementConverter;
import com.illimity.codechallenge.model.Movement;
import com.illimity.codechallenge.model.MovementInputModel;
import com.illimity.codechallenge.repository.MovementRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovementServiceTest extends BaseUnitTest {

    @Mock
    private MovementRepository movementRepository;
    @Mock
    private MovementConverter movementConverter;

    @InjectMocks
    private MovementService movementService;

    @Test
    void createMovement_success(){
        MovementInputModel movementInputModel = random(MovementInputModel.class);
        Movement movement = random(Movement.class);

        when(movementConverter.toMovement(movementInputModel)).thenReturn(movement);
        when(movementRepository.save(movement)).thenReturn(movement);

        Movement result = movementService.createMovement(movementInputModel);

        assertThat(result)
                .isNotNull()
                .isEqualTo(movement);

        verify(movementConverter).toMovement(movementInputModel);
        verify(movementRepository).save(movement);
    }

    @Test
    void findAllMovementsByCustomerId_success(){
        UUID customerId = UUID.randomUUID();
        List<Movement> movements = IntStream.range(1,21)
                .mapToObj(i -> {
                    Movement movement = random(Movement.class);
                    movement.setDate(LocalDateTime.now().plusMinutes(i));
                    return movement;
                })
                .collect(Collectors.toList());

        when(movementRepository.findAllByCustomerId(any(), any())).thenReturn(movements.subList(10, movements.size()));

        List<Movement> result = movementService.findAllMovementsByCustomerId(customerId, Pageable.unpaged());

        assertThat(result).isEqualTo(movements.subList(10, movements.size()));

        verify(movementRepository).findAllByCustomerId(customerId, Pageable.unpaged());
    }

}