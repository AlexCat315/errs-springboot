package com.x.backend.annotation;

import com.x.backend.constants.RoleConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 自定义权限注解
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleSecurity {
    String[] value() default RoleConstants.ROLE_ADMIN; // 默认角色为匿名
}
