package com.ssang.gtd.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_PARAMS(HttpStatus.BAD_REQUEST, "필수데이터 누락, 또는 형식과 다른 데이터를 요청하셨습니다.")
    , UNAUTORIZED(HttpStatus.UNAUTHORIZED, "토큰 정보가 유효하지 않습니다.")
    , JWT_REFRESH_EXPRIRED(HttpStatus.UNAUTHORIZED, "리프레시 토큰...")
    , JWT_ACCESS_NOT_VALID(HttpStatus.UNAUTHORIZED, "리프레시 토큰...")
    , JWT_REFRESH_NOT_VALID(HttpStatus.UNAUTHORIZED, "리프레시 토큰...")
    , UNAVAILABLE(HttpStatus.UNAUTHORIZED, "회원가입이 완료되지 않은 사용자입니다.")
    , NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 데이터입니다.")
    , CAN_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "해당 ID로 조회 가능한 데이터가 없습니다.")
    , DIFFRENT_WRITER(HttpStatus.CONFLICT, "작성자가 아닙니다.")
    , ALREADY_USER(HttpStatus.CONFLICT, "이미 존재하는 회원입니다.")
    , ALREADY_SOCIAL_USER(HttpStatus.CONFLICT, "이미 존재하는 소셜 로그인 회원입니다.")
    , CONFLICT(HttpStatus.CONFLICT, "데이터가 충돌되었습니다.")
    ;

    ErrorCode (HttpStatus httpStatus,String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private final HttpStatus httpStatus;
    private String message;
}