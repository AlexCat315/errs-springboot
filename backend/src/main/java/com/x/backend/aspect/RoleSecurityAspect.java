package com.x.backend.aspect;

import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.common.Account;
import com.x.backend.util.JWTUtils;
import jakarta.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RoleSecurityAspect {

    @Resource
    private JWTUtils<Account> jwtUtils;

    // 获取所有Java类和方法是否有@RoleSecurity注解
    // 如果有，则判断当前用户是否有对应的角色权限，如果没有，则返回403错误
    // 如果没有@RoleSecurity注解，则直接放行
    // 例如：@RoleSecurity(role = "admin")注解表示只有admin角色的用户才可以访问该方法
    // 例如：@RoleSecurity(role = {"admin", "user"})注解表示admin或user角色的用户都可以访问该方法
    @Before("@annotation(com.x.backend.annotation.RoleSecurity) || @within(com.x.backend.annotation.RoleSecurity)")
    public void checkRole(JoinPoint joinPoint) throws SecurityException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = joinPoint.getTarget().getClass();

        // 优先获取方法上的注解
        RoleSecurity roleSecurity = method.getAnnotation(RoleSecurity.class);

        // 如果方法上没有注解，则检查类上是否有注解
        if (roleSecurity == null) {
            roleSecurity = targetClass.getAnnotation(RoleSecurity.class);
        }

        // 如果找到注解，进行权限校验
        if (roleSecurity != null) {
            String[] requiredRoles = roleSecurity.value();
            // ... 校验逻辑
            // 如果为：admin，则校验当前用户是否为admin角色
            // 如果为：{"admin", "user", "anonymous"}，则校验当前用户是否为admin或user或anonymous角色
            String role = jwtUtils.getRole();
            for (String requiredRole : requiredRoles) {
                if (role.equals(RoleConstants.ROLE_ADMIN)) {
                    return;
                }
                if (requiredRole.equals("*") || requiredRole.equals(RoleConstants.ROLE_ANONYMOUS)) { // 允许所有角色访问
                    return;
                }
                if (requiredRole.equals(role)) {
                    return;
                }
            }
            throw new SecurityException("权限校验失败");
        }
    }

}
