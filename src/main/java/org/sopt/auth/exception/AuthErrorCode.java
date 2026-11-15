package org.sopt.auth.exception;

import lombok.Getter;
import org.sopt.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode implements ErrorCode{
    UNAUTHORIZED_HEADER(HttpStatus.UNAUTHORIZED, "유효하지 않은 Authorization 헤더입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    AuthErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}