package com.x.backend.constants;

/**
 * 此类为HTTP消息常量类
 */
public class HttpMessageConstants {
    public static final String SUCCESS = "请求成功";
    public static final String ERROR = "请求失败";
    public static final String NOT_FOUND = "未找到资源";
    public static final String BAD_REQUEST = "请求参数错误";
    public static final String UNAUTHORIZED = "未授权";
    public static final String FORBIDDEN = "无权限";
    public static final String INTERNAL_SERVER_ERROR = "服务器内部错误";
    public static final String SERVICE_UNAVAILABLE = "服务不可用";
    // 登录已过期
    public static final String LOGIN_EXPIRED = "登录已过期";
    // 登录失败
    public static final String LOGIN_FAILED = "登录失败";
    // 注册失败
    public static final String REGISTER_FAILED = "注册失败";
    // 密码错误
    public static final String PASSWORD_ERROR = "密码错误";
    // 验证码错误
    public static final String VERIFICATION_CODE_ERROR = "验证码错误";
}
