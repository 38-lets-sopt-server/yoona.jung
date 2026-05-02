package org.sopt.post.execption;

import org.sopt.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    INVALID_POST_CONTENT(HttpStatus.BAD_REQUEST, "게시글 내용은 필수 입력값입니다."),
    INVALID_POST_TITLE(HttpStatus.BAD_REQUEST, "게시글 제목은 필수 입력값입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    // 생성자
    PostErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    // 인터페이스의 메서드들을 오버라이딩(구현)
    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

