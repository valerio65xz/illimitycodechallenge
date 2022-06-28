package com.illimity.codechallenge.exception;

import com.illimity.codechallenge.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BindException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getValidationErrorMessage)
                .collect(Collectors.toList());

        return createErrorResponseEntity(HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException ex) {
        return createErrorResponseEntity(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ResponseException ex) {
        return createErrorResponseEntity(
                ex.getError().getHttpStatus(),
                Collections.singletonList(ex.getError().getMessage()));
    }

    private ResponseEntity<ErrorResponse> createErrorResponseEntity(int status, List<String> message){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

    private String getValidationErrorMessage(ObjectError objectError){
        String field = Optional.ofNullable(objectError)
                .map(ObjectError::getCodes)
                .map(codes -> codes[1].substring(codes[3].length() + 1))
                .map(substring -> substring.concat(" "))
                .orElse("");

        return Optional.ofNullable(objectError)
                .map(ObjectError::getDefaultMessage)
                .map(field::concat)
                .orElse("");
    }

}
