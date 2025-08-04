package com.marensovich.eljur.exceptions.Exceptions;

public class InvalidRegistrationCodeException extends RuntimeException {
    public InvalidRegistrationCodeException(String message) {
        super(message);
    }
}
