package com.illimity.codechallenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movement {

    @Id
    private UUID movementId;

    private UUID customerId;
    private String description;
    private BigDecimal amount;
    private LocalDateTime date;
    private String currency;

}