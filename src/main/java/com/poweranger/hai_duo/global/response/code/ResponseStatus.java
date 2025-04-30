package com.poweranger.hai_duo.global.response.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ResponseStatus {

    private final boolean isSuccess;
    private final String code;
    private final String message;
    private HttpStatus httpStatus;
}

