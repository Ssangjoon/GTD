package com.ssang.gtd.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExControllerAdvice {
    private final Logger log = LoggerFactory.getLogger(getClass());
    // @ResponseStatus(HttpStatus.BAD_REQUEST) : 상태코드도 바꿔주고 싶을 때 사용
    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorCode illegalExHandle(IllegalArgumentException e) {
        log.error("illegalExHandle : {}", e.getMessage(),e);
        return ErrorCode.UNAVAILABLE;
    }*/

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> userExHandle(CustomException e) {
        log.error("userExHandle : {}", e.getErrorCode().getMessage());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }



}