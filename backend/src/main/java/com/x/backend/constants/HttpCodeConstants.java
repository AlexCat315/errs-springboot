package com.x.backend.constants;

/**
 * 此类为HTTP状态码常量类
 */
public class HttpCodeConstants {
    public static final int OK = 200; // 请求成功
    // 未授权
    public static final int UNAUTHORIZED = 401;
    // 权限不足，拒绝访问
    public static final int FORBIDDEN = 403;
    // 请求资源不存在
    public static final int NOT_FOUND = 404;
    // 服务器内部错误
    public static final int INTERNAL_SERVER_ERROR = 500;
    // 服务不可用
    public static final int SERVICE_UNAVAILABLE = 503;
}
