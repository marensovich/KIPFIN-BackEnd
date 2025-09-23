package com.marensovich.eljur.exceptions;

import java.time.LocalDateTime;

/**
 * The type Api error.
 */
public record ApiError(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp
) { }
