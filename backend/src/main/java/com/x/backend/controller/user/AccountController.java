package com.x.backend.controller.user;

import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.user.vo.request.account.LoginVo;
import com.x.backend.service.user.AccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import com.x.backend.util.JWTUtils;
;

@Slf4j
@RestController("userAccountController")
@RequestMapping("/api/user/account")
public class AccountController {

    @Resource(name = "userAccountService")
    private AccountService accountService;
    @Resource
    private JWTUtils<Account> jwtUtils;

    @PostMapping("/login")
    public ResultEntity<String> login(@RequestBody LoginVo loginVo) {
        if (loginVo == null || loginVo.getUsername() == null || loginVo.getPassword() == null
                || loginVo.getUsername().isEmpty() || loginVo.getPassword().isEmpty()) {
            return ResultEntity.failure(HttpMessageConstants.INFO_INCOMPLETE);
        }
        return accountService.login(loginVo);
    }

    @GetMapping("/validate/token")
    public ResultEntity<String> validateToken(@RequestParam String token) {
        try {
            boolean verifyToken = jwtUtils.verifyToken() && RoleConstants.ROLE_USER.equals(jwtUtils.getRole());
            if (verifyToken) {
                return ResultEntity.success();
            }
            return ResultEntity.failure(HttpMessageConstants.LOGIN_EXPIRED);
        } catch (Exception e) {
            return ResultEntity.failure(HttpMessageConstants.TOKEN_CHECK_FAILED);
        }
    }

    @GetMapping("validate/email")
    public ResultEntity<String> validateEmail(
        @RequestParam(required = true) String email
    ) {
        return Optional.of(email) // 这里用 of() 因为 required=true 保证 email 非 null
            .filter(e -> !e.isBlank())               // 过滤空字符串
            .filter(e -> e.contains("@"))            // 校验包含 @
            .filter(e -> e.indexOf("@") > 0)         // 校验 @ 前有内容（local part）
            .map(e -> ResultEntity.success(e))
            .orElse(ResultEntity.failure("Invalid email: empty or format error"));
    }

    @GetMapping("/test")
    public ResultEntity<String> test(@RequestParam(value = "username", defaultValue = "") String username) {
        return ResultEntity.success(username);
    }
}