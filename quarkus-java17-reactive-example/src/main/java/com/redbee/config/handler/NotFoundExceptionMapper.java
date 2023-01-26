package com.redbee.config.handler;

import com.redbee.config.exception.NotFoundException;
import io.quarkus.logging.Log;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper extends ExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(final NotFoundException exception) {
        Log.error(Response.Status.NOT_FOUND.getReasonPhrase(), exception);
        return buildResponseError(
                Response.Status.NOT_FOUND,
                exception,
                exception.getErrorCode().getCode()
        );
    }

}
