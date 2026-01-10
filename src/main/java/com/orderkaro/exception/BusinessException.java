package com.orderkaro.exception;

import com.orderkaro.exception.BaseException;
import com.orderkaro.exception.ErrorCode;

public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message, ErrorCode.BUSINESS_RULE_VIOLATION);
    }
}
