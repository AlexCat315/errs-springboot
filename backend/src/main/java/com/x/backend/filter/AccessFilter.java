package com.x.backend.filter;

import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.BlockConstants;
import com.x.backend.constants.HttpCodeConstants;
import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.constants.RoleConstants;
import com.x.backend.constructor.PathExcludeConstructor;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Account;
import com.x.backend.util.JWTUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
@Component("accessFilter")
public class AccessFilter extends OncePerRequestFilter {

    @Resource
    private PathExcludeConstructor pathExcludeConstructor;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private JWTUtils<Account> jwtUtils;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(
        @NotNull HttpServletRequest request,
        @NotNull HttpServletResponse response,
        @NotNull FilterChain chain
    ) throws ServletException, IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        response.setContentType("application/json;charset=UTF-8"); // 设置Content-Type
        try {
            Boolean hasPermission = hasPermission(request);
            if (!hasPermission) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response
                    .getWriter()
                    .write(
                        ResultEntity.failure(
                            HttpCodeConstants.FORBIDDEN,
                            HttpMessageConstants.FORBIDDEN
                        ).toJSONString()
                    );
                return;
            }
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response
                .getWriter()
                .write(
                    ResultEntity.failure(
                        HttpCodeConstants.UNAUTHORIZED,
                        HttpMessageConstants.UNAUTHORIZED
                    ).toJSONString()
                );
            return;
        }
        chain.doFilter(request, response);
    }

    /**
     * 获取访问URL的路径，并判断是否有权限访问
     *
     * @param request 请求
     * @return 是否有权限访问
     */
    private Boolean hasPermission(HttpServletRequest request) {
        String url = request.getRequestURI();
        if (pathExcludeConstructor.excludePath(url)) {
            return true;
        }
        // 获取该路径对应方法上的注解权限
        if (checkAnnotationOnMethodOrClassIsRole(request)) {
            return true;
        }
        // 验证token是否被列入黑名单
        if (isBlackList()) {
            return false;
        }

        if (url.startsWith("/api/admin")) {
            // 管理员权限
            String role = jwtUtils.getRole();
            return (
                role != null && role.equalsIgnoreCase(RoleConstants.ROLE_ADMIN)
            );
        } else if (url.startsWith("/api/user")) {
            // 用户权限
            String role = jwtUtils.getRole();
            return (
                role != null && role.equalsIgnoreCase(RoleConstants.ROLE_USER)
            );
        }
        return true;
    }

    /*
     * *
     * 验证token是否被列入黑名单
     *
     * @param token 要验证的token
     *
     * @return 是否被列入黑名单 true: 是 false: 否
     */
    private Boolean isBlackList() {
        int id = jwtUtils.getId();
        String jwt = jwtUtils.getToken();
        String value = redisTemplate.opsForValue().get(id + "_" + jwt);
        return BlockConstants.REDIS_LOGOUT_BLOCK.equals(value);
    }

    private boolean checkAnnotationOnMethodOrClassIsRole(
        HttpServletRequest request
    ) {
        try {
            // Get the Spring HandlerMapping bean
            RequestMappingHandlerMapping handlerMapping =
                applicationContext.getBean(
                    "requestMappingHandlerMapping",
                    RequestMappingHandlerMapping.class
                );

            // Get the handler for the request
            Object handler = Objects.requireNonNull(
                handlerMapping.getHandler(request)
            ).getHandler();

            // Proceed if the handler is a HandlerMethod
            if (handler instanceof HandlerMethod handlerMethod) {
                // Get the method
                Method method = handlerMethod.getMethod();

                // Check if the method has the RoleSecurity annotation
                RoleSecurity roleSecurity = method.getAnnotation(
                    RoleSecurity.class
                );
                if (cheekRole(roleSecurity)) return true;

                // If the method doesn't have the annotation, check the class level annotation
                Class<?> controllerClass = method.getDeclaringClass();
                roleSecurity = controllerClass.getAnnotation(
                    RoleSecurity.class
                );
                if (cheekRole(roleSecurity)) return true;
            }
        } catch (RuntimeException e) {
            // Log runtime exceptions
            log.error(
                "Error checking annotation on method or class for request: {}",
                request.getRequestURI(),
                e
            );
            return false; // Return false or handle as needed
        } catch (Exception e) {
            // Catch other exceptions and rethrow as a runtime exception
            log.error(
                "Unexpected error checking annotation on method or class for request: {}",
                request.getRequestURI(),
                e
            );
            throw new RuntimeException(e);
        }
        return false;
    }

    private boolean cheekRole(RoleSecurity roleSecurity) {
        if (roleSecurity != null) {
            // 获取注解中的角色值
            String[] requiredRoles = roleSecurity.value();

            // 判断用户是否有权限
            if (
                (requiredRoles.length == 1 && requiredRoles[0].equals("*")) ||
                requiredRoles[0].equals(RoleConstants.ROLE_ANONYMOUS)
            ) {
                return true;
            }
            for (String requiredRole : requiredRoles) {
                if (
                    requiredRole.equals("*") ||
                    requiredRole.equals(RoleConstants.ROLE_ANONYMOUS)
                ) { // 允许所有角色访问
                    return true;
                }
            }
            String role = jwtUtils.getRole();
            for (String requiredRole : requiredRoles) {
                if (role.equals(RoleConstants.ROLE_ADMIN)) {
                    return false;
                }
                if (requiredRole.equals(role)) {
                    return false;
                }
            }
        }
        return false;
    }
}
