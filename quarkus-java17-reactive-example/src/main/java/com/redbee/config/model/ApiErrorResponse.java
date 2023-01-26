package com.redbee.config.model;

public record ApiErrorResponse(
        String name,
        int status,
        String timestamp,
        int code,
        String detail
) {
}
