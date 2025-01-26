package com.x.backend.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.pojo.ResultEntity;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletResponse response) throws IOException {
        // 1. 提取校验失败的错误消息
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        // 2. 设置响应状态码和内容类型
        response.setStatus(HttpStatus.OK.value()); // 修改为对应的HTTP状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 4. 将对象序列化为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(ResultEntity.failure(errorMsg));

        // 5. 写入响应流
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
