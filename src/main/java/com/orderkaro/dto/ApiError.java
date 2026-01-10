package com.orderkaro.dto;

import java.time.Instant;

import com.orderkaro.exception.ErrorCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiError {

    private Instant timestamp;
    private int status;
    private ErrorCode errorCode;
    private String message;
    private String path;
}
