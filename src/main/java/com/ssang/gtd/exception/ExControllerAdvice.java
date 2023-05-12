package com.ssang.gtd.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExControllerAdvice {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> userExHandle(CustomException e) {
        log.error("ExControllerAdvice userExHandle : {}", e.getErrorCode().getMessage(),e);
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

}