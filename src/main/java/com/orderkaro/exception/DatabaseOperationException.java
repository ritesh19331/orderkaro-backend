package com.orderkaro.exception;
public class DatabaseOperationException extends BaseException {

    public DatabaseOperationException(String message, Throwable cause) {
        super(message, ErrorCode.DATABASE_ERROR);
        initCause(cause);
    }
}
