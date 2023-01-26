package com.redbee.config.handler;

import com.redbee.config.error.ErrorCode;
import io.quarkus.logging.Log;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionMapper extends ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(final Exception exception) {
        Log.error(Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception);
        return buildResponseError(
                Response.Status.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR.getReasonPhrase(),
                ErrorCode.INTERNAL_ERROR.getCode()
        );
    }

}
