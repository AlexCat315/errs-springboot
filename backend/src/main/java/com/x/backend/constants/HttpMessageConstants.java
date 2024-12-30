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
    // 密码不能为空
    public static final String PASSWORD_NOT_NULL = "密码不能为空";
    // 账号或密码错误
    public static final String ACCOUNT_OR_PASSWORD_ERROR = "账号或密码错误";
    // 邮箱不能为空
    public static final String EMAIL_NOT_NULL = "邮箱不能为空";
    //请求频繁，请稍后再试
    public static final String REQUEST_FREQUENT = "请求频繁，请稍后再试";
    // 信息填写不完整
    public static final String INFO_INCOMPLETE = "信息填写不完整";
    // 密码不一致
    public static final String PASSWORD_NOT_MATCH = "密码不一致";
    // 验证码错误或已过期
    public static final String VERIFICATION_CODE_EXPIRED = "验证码错误或已过期";
    // 邀请码错误或已过期
    public static final String INVITATION_CODE_ERROR = "邀请码错误或已过期";
    // 邀请码不能为空
    public static final String INVITATION_CODE_NOT_NULL = "邀请码不能为空";
    // 邮箱错误
    public static final String EMAIL_ERROR = "邮箱错误";
    // 邮箱已被注册
    public static final String EMAIL_REGISTERED = "邮箱已被注册";
    // 该邮箱未注册
    public static final String EMAIL_NOT_REGISTERED = "该邮箱未注册";
    // 账户密码更新失败
    public static final String ACCOUNT_PASSWORD_UPDATE_FAILED = "账户密码更新失败";
    // 该邀请人没有权限
    public static final String INVITER_NO_PERMISSION = "邀请人权限不足";
    // Token校验失败
    public static final String TOKEN_CHECK_FAILED = "Token校验失败";

}
