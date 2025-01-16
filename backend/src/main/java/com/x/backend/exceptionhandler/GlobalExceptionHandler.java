package com.x.backend.exceptionhandler;

import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.pojo.ResultEntity;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SecurityException.class)
    public ResultEntity<String> handleSecurityException(SecurityException e) {
        log.error("SecurityException: {}", e.getMessage());
        return ResultEntity.failure(404, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultEntity<String> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage());
        return ResultEntity.failure(HttpMessageConstants.ERROR);
    }

     @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "JSON 解析失败: " + ex.getMessage();
        log.error(errorMessage);
        return ResultEntity.failure(HttpMessageConstants.JSON_PARSE_FAILED);
    }

}
