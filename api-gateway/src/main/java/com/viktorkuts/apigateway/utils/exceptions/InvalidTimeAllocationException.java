package com.viktorkuts.apigateway.utils.exceptions;

public class InvalidTimeAllocationException extends RuntimeException {
    public InvalidTimeAllocationException(String message) {
        super(message);
    }

    public InvalidTimeAllocationException(Throwable cause) {
        super(cause);
    }

    public InvalidTimeAllocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
