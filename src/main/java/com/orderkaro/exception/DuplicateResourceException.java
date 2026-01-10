package com.orderkaro.exception;


public class DuplicateResourceException extends BaseException {

    public DuplicateResourceException(String message) {
        super(message, ErrorCode.DUPLICATE_RESOURCE);
    }
}
