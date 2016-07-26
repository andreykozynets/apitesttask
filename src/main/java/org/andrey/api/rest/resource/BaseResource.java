package org.andrey.api.rest.resource;

import org.andrey.api.exception.ApiInvalidCallException;
import org.andrey.api.rest.bean.ErrorResponseDto;
import org.andrey.api.rest.Try;
import org.andrey.api.exception.ApiNotFoundException;

import javax.ws.rs.core.Response;


public class BaseResource {

    protected <T> Response.ResponseBuilder tryToResponse(Try<T> aTry) {

        Response.ResponseBuilder response;
        if (aTry.isSuccessful()) {

            response = Response.ok(aTry.getValue());
        } else {

            Exception exception = aTry.getException();
            Response.Status status;
            if (exception instanceof ApiNotFoundException) {

                status = Response.Status.NOT_FOUND;
            } else if (exception instanceof ApiInvalidCallException){

                status = Response.Status.BAD_REQUEST;
            }
            else {

                status = Response.Status.INTERNAL_SERVER_ERROR;
            }

            ErrorResponseDto errorDTO = ErrorResponseDto.build(status.getStatusCode(), exception.getMessage());
            response = Response.status(status).entity(errorDTO);
        }

        return response;
    }

}
