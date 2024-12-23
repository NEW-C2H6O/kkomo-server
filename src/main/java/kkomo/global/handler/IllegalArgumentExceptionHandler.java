package kkomo.global.handler;

import kkomo.global.ApiErrorResponse;
import kkomo.global.ApiErrorResponse.ApiErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(1)
@RestControllerAdvice
public class IllegalArgumentExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResult> handle(final IllegalArgumentException exception) {
        log.info(exception.getMessage(), exception);
        return ApiErrorResponse.error(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}