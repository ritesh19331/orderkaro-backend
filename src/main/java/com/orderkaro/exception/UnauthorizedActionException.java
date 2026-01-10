package com.orderkaro.exception;


public class UnauthorizedActionException extends BaseException {

    public UnauthorizedActionException(String message) {
        super(message, ErrorCode.UNAUTHORIZED_ACTION);
    }
}
