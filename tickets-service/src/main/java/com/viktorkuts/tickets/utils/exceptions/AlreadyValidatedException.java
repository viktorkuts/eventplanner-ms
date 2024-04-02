package com.viktorkuts.tickets.utils.exceptions;

public class AlreadyValidatedException extends RuntimeException {
    public AlreadyValidatedException(String message) {
        super(message);
    }

    public AlreadyValidatedException(Throwable cause) {
        super(cause);
    }

    public AlreadyValidatedException(String message, Throwable cause) {
        super(message, cause);
    }


}
