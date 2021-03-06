package com.illimity.codechallenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    @Id
    private UUID customerId;

    private String name;
    private String surname;
    private String username;
    private String encodedPassword;
    private String fiscalCode;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime lastLoginDate;
    private Status status;
    private String email;
    private String phoneNumber;

}