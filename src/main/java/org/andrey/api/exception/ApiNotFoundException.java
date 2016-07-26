package org.andrey.api.exception;

/**
 * Exception which is thrown every time when some resource is not found.
 * For example, an API call to assign student A to class X is done, but student A does not exist
 * Or edit call is performed on a Class which does not exist
 */
public class ApiNotFoundException extends ApiException {

    public ApiNotFoundException(String message) {
        super(message);
    }

    public ApiNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
