package com.x.backend.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.stereotype.Component;

@Component
public class EncryptUtils {

    /**
     * 加密密码
     *
     * @param password 原始密码
     * @return 盐值:哈希值
     */
    public String encryptPassword(String password) {
        String salt = RandomUtil.randomString(16); // 生成16位随机盐值
        String hashedPassword = SecureUtil.sha256(password + salt);
        return salt + ":" + hashedPassword;
    }

    /**
     * 验证密码是否匹配
     *
     * @param password 原始密码
     * @param storedPassword 盐值:哈希值
     * @return 是否匹配
     */
    public boolean verifyPassword(String password, String storedPassword) {
        if (password == null || storedPassword == null) {
            return false;
        }
        if (password.isEmpty() || storedPassword.isEmpty()) {
            return false;
        }
        String[] parts = storedPassword.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid stored password format");
        }
        String salt = parts[0];
        String storedHash = parts[1];
        String inputHash = SecureUtil.sha256(password + salt);
        return inputHash.equals(storedHash);
    }
}