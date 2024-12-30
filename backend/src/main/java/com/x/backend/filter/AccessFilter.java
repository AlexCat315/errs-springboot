package com.x.backend.filter;


import com.x.backend.constants.RoleConstants;
import com.x.backend.constructor.PathExcludeConstructor;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.util.JWTUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class AccessFilter extends OncePerRequestFilter {

    @Resource
    private PathExcludeConstructor pathExcludeConstructor;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8"); // 设置Content-Type
        try {
            Boolean hasPermission = hasPermission(request);
            if (!hasPermission) {
                response.getWriter().write(ResultEntity.failure(401, "无权限访问").toString());
                return;
            }
        } catch (Exception e) {
            response.getWriter().write(ResultEntity.failure(401, "无权限访问").toString());
            return;
        }
        chain.doFilter(request, response);
    }


    @Resource
    private JWTUtils jwtUtils;

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
        if (url.startsWith("/api/admin")) {
            // 管理员权限
            String role = jwtUtils.getRole();
            return role != null && role.equalsIgnoreCase(RoleConstants.ROLE_ADMIN);
        } else if (url.startsWith("/api/user")) {
            // 用户权限
            String role = jwtUtils.getRole();
            return role != null && role.equalsIgnoreCase(RoleConstants.ROLE_USER);
        } else {
            // 其他权限
            return true;
        }
    }


}
