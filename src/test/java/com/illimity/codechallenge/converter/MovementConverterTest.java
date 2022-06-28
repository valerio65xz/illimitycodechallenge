package com.illimity.codechallenge.converter;

import com.illimity.codechallenge.BaseUnitTest;
import com.illimity.codechallenge.model.Movement;
import com.illimity.codechallenge.model.MovementInputModel;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import static org.assertj.core.api.Assertions.assertThat;

public class MovementConverterTest extends BaseUnitTest {

    @Spy
    private MovementConverter movementConverter;

    @Test
    void toMovement_success(){
        MovementInputModel movementInputModel = random(MovementInputModel.class);

        Movement result = movementConverter.toMovement(movementInputModel);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("movementId", "date")
                .isEqualTo(movementInputModel);
    }

}
