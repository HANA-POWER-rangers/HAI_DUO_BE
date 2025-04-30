package com.poweranger.hai_duo.global.exception;

import com.poweranger.hai_duo.global.response.code.BaseCode;
import com.poweranger.hai_duo.global.response.code.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private final BaseCode code;

    public ResponseStatus getErrorReason() {
        return this.code.getReason();
    }

    public ResponseStatus getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
