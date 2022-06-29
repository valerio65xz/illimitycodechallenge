package com.illimity.codechallenge.controller;

import com.illimity.codechallenge.ControllerTest;
import com.illimity.codechallenge.model.Movement;
import com.illimity.codechallenge.model.MovementInputModel;
import com.illimity.codechallenge.response.ErrorResponse;
import com.illimity.codechallenge.service.MovementService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class MovementControllerTest extends ControllerTest {

    @MockBean
    private MovementService movementService;

    @Test
    public void createMovement_success() {
        String url = "/movements";
        MovementInputModel movementInputModel = random(MovementInputModel.class);
        Movement movement = random(Movement.class);

        when(movementService.createMovement(any())).thenReturn(movement);

        Movement result = performAndExpect(post(url), movementInputModel, Movement.class);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(movement);

        verify(movementService).createMovement(refEq(movementInputModel));
    }

    @Test
    public void createCustomer_failForBeanValidation() {
        String url = "/movements";
        MovementInputModel movementInputModel = new MovementInputModel(
                random(UUID.class),
                random(String.class),
                random(BigDecimal.class),
                null
        );

        ErrorResponse result = performAndExpect(post(url), movementInputModel, ErrorResponse.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(400);
        assertThat(result.getMessage().get(0)).isEqualTo("currency must not be blank");
    }

    @Test
    public void loadMovement_success() {
        UUID movementId = UUID.randomUUID();
        String url = "/movements/" + movementId;
        Movement movement = random(Movement.class);

        when(movementService.findById(movementId)).thenReturn(movement);

        Movement result = performAndExpect(get(url), Movement.class);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(movement);

        verify(movementService).findById(movementId);
    }

    @Test
    public void loadMovements_success() {
        String url = "/movements";
        Movement movement = random(Movement.class);

        when(movementService.findAllMovements()).thenReturn(Collections.singletonList(movement));

        List<Movement> result = performAndExpectWithCollection(get(url), List.class, Movement.class);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isEqualTo(movement);

        verify(movementService).findAllMovements();
    }

    @Test
    public void deleteMovement_success() {
        UUID movementId = UUID.randomUUID();
        String url = "/movements/" + movementId;

        doNothing().when(movementService).deleteMovement(movementId);

        performAndExpect(delete(url));

        verify(movementService).deleteMovement(movementId);
    }

    @Test
    public void deleteMovements_success() {
        String url = "/movements";

        doNothing().when(movementService).deleteMovements();

        performAndExpect(delete(url));

        verify(movementService).deleteMovements();
    }

    @Test
    public void loadPaginatedMovements_success() {
        UUID customerId = UUID.randomUUID();
        String url = "/movements/paginated/" + customerId + "?page=0&size=10&sort=date,DESC";
        Movement movement = random(Movement.class);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("date").descending());

        when(movementService.findAllMovementsByCustomerId(any(), any())).thenReturn(Collections.singletonList(movement));

        List<Movement> result = performAndExpectWithCollection(get(url), List.class, Movement.class);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isEqualTo(movement);

        verify(movementService).findAllMovementsByCustomerId(customerId, pageable);
    }

}
