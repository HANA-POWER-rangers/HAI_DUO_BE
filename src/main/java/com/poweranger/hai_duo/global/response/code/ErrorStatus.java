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
    CHARACTER_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAR4001", "해당 캐릭터가 존재하지 않습니다."),

    // 학습 관련
    STAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "STAGE4001", "해당 스테이지가 존재하지 않습니다."),
    CHAPTER_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAPTER4001", "해당 챕터가 존재하지 않습니다."),

    // 퀴즈 관련
    QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "QUIZ4001", "해당 퀴즈가 존재하지 않습니다."),
    QUIZ_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "QUIZ4002", "그 어떤 퀴즈 타입도 존재하지 않습니다."),
    QUIZ_MEAN_NOT_FOUND(HttpStatus.NOT_FOUND, "QUIZ4003", "MEANING 퀴즈 타입이 존재하지 않습니다."),
    QUIZ_OX_NOT_FOUND(HttpStatus.NOT_FOUND, "QUIZ4004", "OX 퀴즈 타입이 존재하지 않습니다."),
    QUIZ_CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "QUIZ4005", "CARD 퀴즈 타입이 존재하지 않습니다."),
    QUIZ_BLANK_NOT_FOUND(HttpStatus.NOT_FOUND, "QUIZ4006", "BLANK 퀴즈 타입이 존재하지 않습니다.");


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
