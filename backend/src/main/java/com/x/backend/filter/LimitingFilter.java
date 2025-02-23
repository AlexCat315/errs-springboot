package com.x.backend.filter;


import com.x.backend.pojo.ResultEntity;
import com.x.backend.util.FlowUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
public class LimitingFilter extends OncePerRequestFilter {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    //指定时间内最大请求次数限制
    @Value("${spring.web.flow.limit}")
    private int limit;
    //计数时间周期
    @Value("${spring.web.flow.period}")
    private int period;
    //超出请求限制封禁时间
    @Value("${spring.web.flow.block}")
    private int block;

    @Resource
    private FlowUtils flowUtils;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws IOException, ServletException {
        String address = getClientIpAddress(request);
        val method = request.getMethod();
        if (!tryCount(address, method))
            this.writeBlockMessage(response);
        else
            chain.doFilter(request, response);
    }

    /**
     * 尝试对指定IP地址请求计数，如果被限制则无法继续访问
     *
     * @param address 请求IP地址
     * @return 是否操作成功
     */
    private boolean tryCount(String address, String method) {
        synchronized (address.intern()) {
            if (Boolean.TRUE.equals(stringRedisTemplate.hasKey("flow:block:" + address + ":" + method)))
                return false;
            String counterKey = "flow:counter:" + address + ":" + method;
            String blockKey = "flow:block:" + address + ":" + method;
            return flowUtils.limitPeriodCheck(counterKey, blockKey, block, limit, period);
        }
    }

    /**
     * 为响应编写拦截内容，提示用户操作频繁
     *
     * @param response 响应
     * @throws IOException 可能的异常
     */
    private void writeBlockMessage(HttpServletResponse response) throws IOException {
        response.setStatus(429);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(ResultEntity.failure(429, "请求频率过快，请稍后再试").toJSONString());
    }

    private String getClientIpAddress(HttpServletRequest request) {
        List<String> headers = Arrays.asList(
                "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP"
        );
        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.trim().isEmpty() && !ip.equalsIgnoreCase("unknown")) {
                String[] ips = ip.split(",");
                return ips[0].trim();
            }
        }
        String ip = request.getRemoteAddr();
        String[] ips = ip.split(",");
        return ips[0].trim();
    }


}

