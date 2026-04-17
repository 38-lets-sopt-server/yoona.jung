package org.sopt.global.exception.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String timeStamp,
        int status,
        String error,
        String message,
        String path
) {
    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(
                LocalDateTime.now().toString(),
                status,
                error,
                message,
                path
        );
    }
}
