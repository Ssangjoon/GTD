package com.ssang.gtd.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Builder
@Getter
public class ErrorResponse {
    private int status;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(e.getHttpStatus().value())
                        .code(e.name())
                        .message(e.getMessage())
                        .build()
                );
    }

}
