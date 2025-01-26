package com.x.backend.constants;

public class BlockConstants {

    public static final String REDIS_LOGOUT_BLOCK = "REDIS_LOGOUT_BLOCK"; // 存在redis中，用于记录用户登出黑名单的key
    public static final String REDIS_USER_REGISTER_VALIDATE_EMAIL = "USER_REGISTER_VALIDATE_EMAIL"; // 用户注册时验证邮箱时的key
    public static final String REDIS_USER_FORGOTPASSWORD_CODE = "REDIS_USER_FORGOTPASSWORD_CODE"; // 忘记密码时的Redis key
}
