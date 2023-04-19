package com.ssang.gtd.utils.cons;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorCode {
    INVALID_PARAMS(400, "InvalidParams", "필수데이터 누락, 또는 형식과 다른 데이터를 요청하셨습니다.")
    , UNAUTORIZED(401, "Unauthorized", "토큰 정보가 유효하지 않습니다.")
    , UNAVAILABLE(401, "Unavailable", "회원가입이 완료되지 않은 사용자입니다.")
    , NOT_FOUND(404, "NotFound", "존재하지 않는 데이터입니다.")
    , CONFLICT(409, "Conflict", "데이터가 충돌되었습니다.")
    ;

    ErrorCode (int status,String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    private int status;
    private String code;
    private String message;

    public static ErrorCode valueOfCode(String errorCode) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(errorCode))
                .findAny()
                .orElse(null);
    }

}