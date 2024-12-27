package com.x.backend.util;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.x.backend.pojo.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JWTUtils {


    public static final String KEY = "YUAN SHEN GAO SHOU SHI CAI BI , TAI KEN LE , TOU JU YOU";


    /**
     * @param account    用户信息
     * @param expireTime 过期时间（天）
     */
    public String createJWT(Account account, Integer expireTime) {
        Long EXPIRE_DAY = System.currentTimeMillis() + 1000L * 60 * 60 * 24 * expireTime;

        Map<String, Object> map = new HashMap<>() {
            {
                put("uid", account.getAId());
                put("role", account.getRole());
                put("expire_time", EXPIRE_DAY);
            }
        };
        return JWTUtil.createToken(map, KEY.getBytes(StandardCharsets.UTF_8));
    }

    public JWTPayload analysisJWT(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.getPayload();
    }

    public Integer getId(String token) {
        try {
            if (token != null && !token.isEmpty()) {
                JWTPayload jwtPayload = analysisJWT(token);
                Object uid = jwtPayload.getClaim("uid");
                return Integer.parseInt(uid.toString());
            }
        } catch (RuntimeException e) {

            throw new RuntimeException("JWT校验错误");
        }
        throw new RuntimeException("JWT校验错误");
    }

    public Long getExpireTime(String token) {
        try {
            if (token != null && !token.isEmpty()) {
                JWTPayload jwtPayload = analysisJWT(token);
                Object expireTime = jwtPayload.getClaim("expire_time");
                return Long.parseLong(expireTime.toString());
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("JWT校验错误");
        }
        throw new RuntimeException("JWT校验错误");
    }
    public String getRole(String token) {
        try {
            if (token != null && !token.isEmpty()) {
                JWTPayload jwtPayload = analysisJWT(token);
                Object role = jwtPayload.getClaim("role");
                return role.toString();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("JWT校验错误");
        }
        throw new RuntimeException("JWT校验错误");
    }

    public String getToken(String token) {
        if (token == null){
            throw new RuntimeException("JWT校验错误");
        }
        // 截取有效 token 部分
        return token.substring(7);
    }

}

