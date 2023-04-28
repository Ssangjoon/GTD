package com.ssang.gtd.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_PARAMS(HttpStatus.BAD_REQUEST, "필수데이터 누락, 또는 형식과 다른 데이터를 요청하셨습니다.")
    , UNAUTORIZED(HttpStatus.UNAUTHORIZED, "토큰 정보가 유효하지 않습니다.")
    , UNAVAILABLE(HttpStatus.UNAUTHORIZED, "회원가입이 완료되지 않은 사용자입니다.")
    , NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 데이터입니다.")
    , CONFLICT(HttpStatus.CONFLICT, "데이터가 충돌되었습니다.")
    ;

    ErrorCode (HttpStatus httpStatus,String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private final HttpStatus httpStatus;
    private String message;

    /*public static ErrorCode valueOfCode(String errorCode) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(errorCode))
                .findAny()
                .orElse(null);
    }*/

}