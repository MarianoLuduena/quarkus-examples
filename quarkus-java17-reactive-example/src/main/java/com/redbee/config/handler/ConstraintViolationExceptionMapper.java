package com.redbee.config.handler;

import com.redbee.config.error.ErrorCode;
import io.quarkus.logging.Log;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper
        extends ExceptionHandler implements ExceptionMapper<ConstraintViolationException>  {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        Log.error(Response.Status.BAD_REQUEST.getReasonPhrase(), exception);
        return buildResponseError(
                Response.Status.BAD_REQUEST,
                exception,
                ErrorCode.BAD_REQUEST.getCode()
        );
    }

}
