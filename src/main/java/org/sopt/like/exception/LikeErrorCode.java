package org.sopt.like.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LikeErrorCode implements ErrorCode {

    ALREADY_LIKED_POST(HttpStatus.CONFLICT, "이미 좋아요를 누른 게시물입니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요 기록을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
