package com.illimity.codechallenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerOutputModel {

    private String name;
    private String surname;
    private String fiscalCode;
    private String email;
    private String phoneNumber;
    private Status status;

}