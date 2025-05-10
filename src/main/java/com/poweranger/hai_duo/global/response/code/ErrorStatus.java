package com.poweranger.hai_duo.global.response.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {

    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 멤버 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4001", "사용자가 없습니다."),

    // 레벨 관련
    LEVEL_NOT_FOUND(HttpStatus.NOT_FOUND, "LEVEL4001", "해당 레벨이 존재하지 않습니다."),

    // 캐릭터 관련
    CHARACTER_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAR4001", "해당 캐릭터가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ResponseStatus getReason() {
        return ResponseStatus.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ResponseStatus getReasonHttpStatus() {
        return ResponseStatus.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
