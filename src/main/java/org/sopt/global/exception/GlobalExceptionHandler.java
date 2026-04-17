package org.sopt.global.exception;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.sopt.global.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e, HttpServletRequest request) {
        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = ErrorResponse.of(
                errorCode.getHttpStatus().value(),
                errorCode.getHttpStatus().name(),
                errorCode.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.of(
                500,
                "INTERNAL_SERVER_ERROR",
                e.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
