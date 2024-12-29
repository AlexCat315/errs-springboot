package com.x.backend.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.x.backend.annotation.VipSecurity;
import com.x.backend.util.JWTUtils;
import jakarta.annotation.Resource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@Component
public class VipSecurityAspect {


    @Resource
    private JWTUtils jwtUtils;

    @Before("execution(* *(..))")
    public void checkVip(JoinPoint joinPoint) throws SecurityException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = joinPoint.getTarget().getClass();

        // 优先获取方法上的注解
        VipSecurity vipSecurity = method.getAnnotation(VipSecurity.class);

        // 如果方法上没有注解，则检查类上是否有注解
        if (vipSecurity == null) {
            vipSecurity = targetClass.getAnnotation(VipSecurity.class);
        }
        if (vipSecurity != null) {
            String vipLevel = jwtUtils.getVip();
            if (vipLevel == null) {
                throw new SecurityException("Invalid token");
            }
            String level = vipSecurity.level();
            // 如果注解的level为空，则表示不需要判断vip等级
            if (level.isEmpty()) {
                return;
            }

            // 截取注解的字符
            String lastChar = level.substring(level.length() - 1);
            String prefix = level.substring(0, level.length() - 1);
            if (prefix.equalsIgnoreCase("vip")) { // 注解中的要求为vip，则权限等级为vip和svip都可以
                // 截取权限的最后一个字符
                String tokenVipLevel = vipLevel.substring(vipLevel.length() - 1);
                int tokenVipLevelInt = Integer.parseInt(tokenVipLevel);
                int levelInt = Integer.parseInt(lastChar);
                if (tokenVipLevelInt < levelInt) {
                    throw new SecurityException("No permission");
                }
            } else if (prefix.equalsIgnoreCase("svip")) { // 注解中的要求为svip，则权限等级为svip才可以
                // 截取权限中之前的字符
                String tokenPrefix = vipLevel.substring(0, vipLevel.length() - 1);
                if (!tokenPrefix.equalsIgnoreCase("svip")) {
                    throw new SecurityException("No permission");
                }
                // 截取权限的最后一个字符
                String tokenVipLevel = vipLevel.substring(vipLevel.length() - 1);
                int tokenVipLevelInt = Integer.parseInt(tokenVipLevel);
                int levelInt = Integer.parseInt(lastChar);
                if (tokenVipLevelInt < levelInt || tokenVipLevelInt == 0) {
                    throw new SecurityException("No permission");
                }
            } else { // 注解中的要求为vip或svip，但是权限等级不符合要求
                throw new SecurityException("No permission");
            }
        }

    }

}
