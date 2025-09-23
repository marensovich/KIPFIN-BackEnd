package com.marensovich.eljur.exceptions.Exceptions;

/**
 * Thrown when a JWT token does not match the expected format.
 *
 * <p>For example, if it does not contain the required two dots.</p>
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
public class InvalidJwtTokenFormat extends RuntimeException {

    /**
     * Instantiates a new Invalid JWT token format exception.
     *
     * @param message the error message
     */
    public InvalidJwtTokenFormat(String message) {
        super(message);
    }
}
