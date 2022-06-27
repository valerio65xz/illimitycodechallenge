package com.illimity.codechallenge.exception;

public class ResponseException extends RuntimeException {

    private final ResponseError error;
    private final String message;

    public ResponseException(ResponseError error) {
        this.error = error;
        this.message = null;
    }

    public ResponseError getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}