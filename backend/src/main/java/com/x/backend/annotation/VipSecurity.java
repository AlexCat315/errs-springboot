package com.x.backend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// This annotation is used to mark the controller methods that require vip access.
// 这个注解用于标记需要vip权限的控制器方法。
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VipSecurity {
    // vip 等级示例 [vip1; vip2; svip1; svip2]
    String level() default "";
}
