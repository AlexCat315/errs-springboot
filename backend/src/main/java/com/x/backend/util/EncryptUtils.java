package com.x.backend.util;

import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptUtils {

    @Value("${salt.key}")
    private static String saltKey;

    /**
     * 使用SHA-256和盐值加密密码
     *
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String password) {
        return SecureUtil.sha256(password + saltKey);
    }
}
