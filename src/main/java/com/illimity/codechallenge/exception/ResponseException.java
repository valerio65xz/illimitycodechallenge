package com.illimity.codechallenge.exception;

public class ResponseException extends RuntimeException {

    private final ResponseErrorEnum error;

    public ResponseException(ResponseErrorEnum error) {
        this.error = error;
    }

    public ResponseErrorEnum getError() {
        return error;
    }

}