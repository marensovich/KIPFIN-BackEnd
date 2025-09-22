package com.marensovich.eljur.exceptions.Exceptions;

public class InvalidJwtTokenFormat extends RuntimeException {
    public InvalidJwtTokenFormat(String message) {
        super(message);
    }
}
