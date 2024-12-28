package com.x.backend.exceptionhandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.x.backend.pojo.ResultEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(SecurityException.class)
    public ResultEntity<String> handleSecurityException(SecurityException e) {
        log.error("SecurityException: {}", e.getMessage());
        return ResultEntity.failure(404, e.getMessage());
    }
}
