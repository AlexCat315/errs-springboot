package com.x.backend.util;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.x.backend.pojo.admin.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JWTUtils {

    private final HttpServletRequest request;

    public JWTUtils(HttpServletRequest request) {
        this.request = request;
    }

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
                put("vip", account.getVip());

            }
        };
        return JWTUtil.createToken(map, KEY.getBytes(StandardCharsets.UTF_8));
    }

    public JWTPayload analysisJWT(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.getPayload();
    }

    // 获取用户ID
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

    public Integer getId() {
        return getId(getHeaderToken());
    }

    // 获取过期时间
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

    public Long getExpireTime() {
        return getExpireTime(getHeaderToken());
    }

    // 获取角色
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

    public String getRole() {

        return getRole(getHeaderToken());
    }

    // 获取VIP等级
    public String getVip(String token) {
        try {
            if (token != null && !token.isEmpty()) {
                JWTPayload jwtPayload = analysisJWT(token);
                Object vip = jwtPayload.getClaim("vip");
                return vip.toString();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("JWT校验错误");
        }
        throw new RuntimeException("JWT校验错误");
    }

    public String getVip() {
        return getVip(getHeaderToken());
    }

    // 获取有效 token 部分
    public String getToken(String token) {
        if (token == null) {
            throw new RuntimeException("JWT校验错误");
        }
        // 截取有效 token 部分
        return token.substring(7);
    }

    private String getHeaderToken() {
       return request.getHeader("Authorization");
    }

}
