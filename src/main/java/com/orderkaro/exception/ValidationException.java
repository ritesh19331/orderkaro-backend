package com.orderkaro.exception;


public class ValidationException extends BaseException {

    public ValidationException(String message) {
        super(message, ErrorCode.VALIDATION_FAILED);
    }
}
