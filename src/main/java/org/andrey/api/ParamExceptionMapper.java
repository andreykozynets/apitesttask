package org.andrey.api;

import org.andrey.api.exception.ApiInvalidCallException;
import org.andrey.api.exception.ApiNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * This is an alternative way of wrapping the exceptions. Can be used instead of the wrapping that happens in Try.wrap method
 * and BaseResource.tryToResponse method
 */
@Provider
public class ParamExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof ApiInvalidCallException){

            return Response.status(Response.Status.BAD_REQUEST).entity("Api invalid call exception occurred").build();
        } else if (exception instanceof ApiNotFoundException){

            return Response.status(Response.Status.NOT_FOUND).entity("Api exception occurred. Resource was not found").build();
        }
        else {

            return Response.status(Response.Status.BAD_REQUEST).entity("Unexpected exception occurred").build();
        }
    }
}