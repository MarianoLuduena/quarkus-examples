package com.redbee.config.exception;

import com.redbee.config.error.ErrorCode;

abstract class GenericException extends RuntimeException {

    private final ErrorCode errorCode;

    protected GenericException(final ErrorCode errorCode) {
        super(errorCode.getReasonPhrase());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

}
