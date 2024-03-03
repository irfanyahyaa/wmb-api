package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.dto.response.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<CommonResponse<?>> responseStatusExceptionHandler(
            ResponseStatusException exception
    ) {
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .statuscode(exception.getStatusCode().value())
                .message(exception.getReason())
                .build();

        return ResponseEntity
                .status(exception.getStatusCode())
                .body(commonResponse);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<CommonResponse<?>> constraintViolationExceptionHandler(
            ConstraintViolationException exception
    ) {
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .statuscode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(commonResponse);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<CommonResponse<?>> methodArgumentTypeMismatchExceptionHandler(
            MethodArgumentTypeMismatchException exception
    ) {
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .statuscode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(commonResponse);
    }
}
