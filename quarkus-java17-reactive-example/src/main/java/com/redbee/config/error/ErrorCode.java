package com.redbee.config.error;

public enum ErrorCode {

    INTERNAL_ERROR(100, "Internal Server Error"),
    BAD_REQUEST(101, "Bad Request"),
    RESOURCE_NOT_FOUND(102, "Resource not found"),
    REQUEST_TIMEOUT(103, "Operation timed out"),
    BAD_GATEWAY(104, "Bad Gateway");

    private final int code;
    private final String reasonPhrase;

    ErrorCode(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public int getCode() {
        return code;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

}
