package com.redbee.config.handler;

import com.redbee.config.model.ApiErrorResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

abstract class ExceptionHandler {

    protected Response buildResponseError(
            final Response.StatusType httpStatus,
            final Throwable ex,
            final int errorCode
    ) {
        return doBuildResponseError(httpStatus, buildApiErrorResponse(httpStatus, errorCode, ex.getMessage()));
    }

    protected Response buildResponseError(
            final Response.StatusType httpStatus,
            final String errorMessage,
            final int errorCode
    ) {
        return doBuildResponseError(httpStatus, buildApiErrorResponse(httpStatus, errorCode, errorMessage));
    }

    private ApiErrorResponse buildApiErrorResponse(
            final Response.StatusType httpStatus,
            final int errorCode,
            final String detail
    ) {
        return new ApiErrorResponse(
                httpStatus.getReasonPhrase(),
                httpStatus.getStatusCode(),
                LocalDateTime.now(ZoneOffset.UTC).toString(),
                errorCode,
                Optional.ofNullable(detail).orElse("")
        );
    }

    private Response doBuildResponseError(
            final Response.StatusType httpStatus,
            final ApiErrorResponse apiErrorResponse
    ) {
        return Response
                .status(httpStatus)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(apiErrorResponse)
                .build();
    }

}
