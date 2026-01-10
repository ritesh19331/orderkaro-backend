package com.orderkaro.exception;

public enum ErrorCode {

    // Generic
    INTERNAL_SERVER_ERROR,
    INVALID_REQUEST,

    // Resource
    RESOURCE_NOT_FOUND,
    DUPLICATE_RESOURCE,

    // Authorization
    UNAUTHORIZED_ACTION,
    FORBIDDEN,

    // Business
    INVALID_SHOP_STATE,
    BUSINESS_RULE_VIOLATION,

    // Validation
    VALIDATION_FAILED,

    // Infrastructure
    DATABASE_ERROR
}
