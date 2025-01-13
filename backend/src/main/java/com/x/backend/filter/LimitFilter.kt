package com.x.backend.filter

import com.x.backend.constants.HttpCodeConstants
import com.x.backend.constants.HttpMessageConstants
import com.x.backend.exception.ForbiddenException
import com.x.backend.pojo.ResultEntity
import jakarta.annotation.Resource
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@Slf4j
@Component
class LimitFilter : OncePerRequestFilter() {

    @Resource
    private lateinit var redisTemplate: RedisTemplate<Any, Any>

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            limitRequest(request)
            filterChain.doFilter(request, response)
        } catch (e: ForbiddenException) {
            response.contentType = "application/json;charset=UTF-8"
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.writer.write(
                ResultEntity
                    .failure<Any>(HttpCodeConstants.FORBIDDEN, e.message).toJSONString()
            )
        }
    }

    private fun limitRequest(request: HttpServletRequest) {
        val ip = getClientIpAddress(request)
        val requestURI = request.requestURI
        logger.info("LimitFilter: ip: $ip, requestURI: $requestURI")
        val key = "limit_ip:$requestURI$ip"
        val maxRequests = 10
        val expireTime = 1L
        val script = """
            local current = redis.call('INCR', KEYS[1])
            if current == 1 then
                redis.call('EXPIRE', KEYS[1], ARGV[1])
            end
            return current
        """
        val currentCount = redisTemplate.execute(
            RedisScript.of(script, Long::class.java),
            listOf(key),
            expireTime.toString()
        ) ?: 0

        if (currentCount > maxRequests) {
            throw ForbiddenException("请求过于频繁，请稍后再试")
        }
    }

    private fun getClientIpAddress(request: HttpServletRequest): String {
        val headers = listOf(
            "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP"
        )
        val ip = headers.asSequence()
            .mapNotNull { request.getHeader(it) }
            .firstOrNull { it.isNotBlank() && !"unknown".equals(it, ignoreCase = true) }
            ?: request.remoteAddr

        return ip.split(",").first().trim()
    }
}


