package com.illimity.codechallenge.exception;

public class ResponseException extends RuntimeException {

    private final ResponseErrorEnum error;
    private final String message;

    public ResponseException(ResponseErrorEnum error) {
        this.error = error;
        this.message = null;
    }

    public ResponseErrorEnum getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}