package com.marensovich.eljur.exceptions.Exceptions;

/**
 * The type Invalid jwt token format.
 */
public class InvalidJwtTokenFormat extends RuntimeException {
    /**
     * Instantiates a new Invalid jwt token format.
     *
     * @param message the message
     */
    public InvalidJwtTokenFormat(String message) {
        super(message);
    }
}
