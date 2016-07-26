package org.andrey.api.exception;


/**
 * This exception is thrown every time an invalid API call is made
 * For example, an edit student call is done, but the request does not contain body with the list of attributes to update
 */
public class ApiInvalidCallException extends ApiException {

    public ApiInvalidCallException(String message) {
        super(message);
    }

    public ApiInvalidCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
