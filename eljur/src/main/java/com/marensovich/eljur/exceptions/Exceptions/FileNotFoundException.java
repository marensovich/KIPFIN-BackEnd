package com.marensovich.eljur.exceptions.Exceptions;

/**
 * The type File not found exception.
 */
public class FileNotFoundException extends RuntimeException {
    /**
     * Instantiates a new File not found exception.
     *
     * @param message the message
     */
    public FileNotFoundException(String message) {
        super(message);
    }
}

