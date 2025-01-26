package com.x.backend.controller.user;

import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.user.vo.request.ValidateEmailCode;
import com.x.backend.pojo.user.vo.request.account.LoginVo;
import com.x.backend.service.user.AccountService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import com.x.backend.util.JWTUtils;;

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
            @RequestParam(required = true) String email) {

        // 1. 检查邮箱是否为空或仅包含空白字符
        if (email.isBlank()) {
            return ResultEntity.failure("Invalid email: empty or format error");
        }

        // 2. 检查是否包含 @ 符号
        if (!email.contains("@")) {
            return ResultEntity.failure("Invalid email: empty or format error");
        }

        // 3. 检查 @ 符号的位置（不能在开头）
        int atIndex = email.indexOf("@");
        if (atIndex <= 0) { // @ 在第一个字符或未找到（但上一步已确保存在）
            return ResultEntity.failure("Invalid email: empty or format error");
        }

        // 所有格式校验通过，调用服务层验证邮箱是否已注册
        return accountService.validateEmail(email);
    }

    @PostMapping("validate/email-code")
    public ResultEntity<String> ValidateEmailCode(@Valid @RequestBody ValidateEmailCode validateEmailCode) {
        
        return ResultEntity.success();
    }

    @GetMapping("/test")
    public ResultEntity<String> test(@RequestParam(value = "username", defaultValue = "") String username) {
        return ResultEntity.success(username);
    }
}