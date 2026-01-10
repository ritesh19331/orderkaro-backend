package com.orderkaro.exception;


public class InvalidShopStateException extends BaseException {

    public InvalidShopStateException(String message) {
        super(message, ErrorCode.INVALID_SHOP_STATE);
    }
}
