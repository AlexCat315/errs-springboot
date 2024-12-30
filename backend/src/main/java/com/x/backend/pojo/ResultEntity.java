package com.x.backend.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.x.backend.constants.HttpCodeConstants;
import com.x.backend.constants.HttpMessageConstants;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResultEntity<T> implements Serializable {
    private Integer code;
    private T data;
    private String message;


    // 默认构造函数，成功状态
    public ResultEntity() {
        this.code = HttpCodeConstants.OK;
        this.message = HttpMessageConstants.SUCCESS;
        this.data = null;
    }

    // 带数据的成功构造函数
    public ResultEntity(T data) {
        this.code = HttpCodeConstants.OK;
        this.message = HttpMessageConstants.SUCCESS;
        this.data = data;
    }

    // 带状态码和消息的构造函数
    public ResultEntity(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    // 带状态码、消息和数据的构造函数
    public ResultEntity(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 静态方法，用于返回成功的响应
    public static <T> ResultEntity<T> success() {
        return new ResultEntity<>();
    }

    public static <T> ResultEntity<T> success(T data) {
        return new ResultEntity<>(data);
    }

    public static <T> ResultEntity<T> success(String msg, T data) {
        return new ResultEntity<>(HttpCodeConstants.OK, msg, data);
    }

    // 静态方法，用于返回失败的响应
    public static <T> ResultEntity<T> failure(Integer code, String msg) {
        return new ResultEntity<>(code, msg, null);
    }

    // 静态方法，用于返回服务器内部错误的响应
    public static <T> ResultEntity<T> serverError() {
        return new ResultEntity<>(HttpCodeConstants.INTERNAL_SERVER_ERROR, HttpMessageConstants.INTERNAL_SERVER_ERROR, null);
    }

    // toString方法，将对象转换为json字符串，利用jackson的ObjectMapper类
    public String toJSONString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return this.toString();
        }
    }
}

