package org.andrey.api.exception;

/**
 * Base class for any exception thrown within the API
 */
public class ApiException extends RuntimeException{

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
