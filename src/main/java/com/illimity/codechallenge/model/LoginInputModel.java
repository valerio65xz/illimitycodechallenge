package com.illimity.codechallenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@ToString
public class LoginInputModel {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}