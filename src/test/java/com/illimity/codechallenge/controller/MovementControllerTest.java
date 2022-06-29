package com.illimity.codechallenge.controller;

import com.illimity.codechallenge.ControllerTest;
import com.illimity.codechallenge.model.Movement;
import com.illimity.codechallenge.service.MovementService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class MovementControllerTest extends ControllerTest {

    @MockBean
    private MovementService movementService;

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
