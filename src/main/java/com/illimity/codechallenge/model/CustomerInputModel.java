package com.illimity.codechallenge.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class CustomerInputModel {

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String fiscalCode;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private Status status;

}