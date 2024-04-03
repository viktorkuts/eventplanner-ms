package com.viktorkuts.venues.utils.exceptions;

public class InvalidPostalCodeException extends RuntimeException {
    public InvalidPostalCodeException(String message) {
        super(message);
    }

    public InvalidPostalCodeException(Throwable cause) {
        super(cause);
    }

    public InvalidPostalCodeException(String message, Throwable cause) {
        super(message, cause);
    }


}
