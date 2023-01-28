package com.redbee.config.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ApiErrorResponse(
        String name,
        int status,
        String timestamp,
        int code,
        String detail
) {
}
