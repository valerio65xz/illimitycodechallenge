package com.illimity.codechallenge.exception;

public enum ResponseError {

    BAD_REQUEST("001", "Bad request", 400),
    STATUS_NOT_VALID("002", "Customer status must be active", 400),
    USER_ALREADY_EXISTS("002", "A user with the specified username already exists", 400),
    UNAUTHORIZED("003", "Unauthorized", 401),
    CUSTOMER_NOT_FOUND("004", "Customer not found", 404);

    private final String code;
    private final String message;
    private final int httpStatus;

    ResponseError(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

}
