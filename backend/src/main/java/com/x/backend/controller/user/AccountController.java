package com.x.backend.controller.user;

import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.BlockConstants;
import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.constants.RoleConstants;
import com.x.backend.exception.ForbiddenException;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.user.dto.account.ValidateEmailCodeDTO;
import com.x.backend.pojo.user.entity.UserAccount;
import com.x.backend.pojo.user.vo.request.account.ForgotPassowrdVo;
import com.x.backend.pojo.user.vo.request.account.LoginVo;
import com.x.backend.pojo.user.vo.request.account.RegisterVo;
import com.x.backend.pojo.user.vo.request.account.ValidateEmailCode;
import com.x.backend.service.user.AccountService;
import com.x.backend.util.TimeUtils;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.x.backend.util.EncryptUtils;
import com.x.backend.util.JWTUtils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

@Slf4j
@RestController("userAccountController")
@RequestMapping("/api/user/account")
@RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
public class AccountController {

    @Resource(name = "userAccountService")
    private AccountService accountService;
    @Resource
    private JWTUtils<Account> jwtUtils;
    @Resource
    private EncryptUtils encryptUtils;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private TimeUtils timeUtils;

    @PostMapping("/login")
    public ResultEntity<String> login(@RequestBody LoginVo loginVo) {
        if (loginVo == null || loginVo.getUsername() == null || loginVo.getPassword() == null
                || loginVo.getUsername().isEmpty() || loginVo.getPassword().isEmpty()) {
            return ResultEntity.failure(HttpMessageConstants.INFO_INCOMPLETE);
        }
        return accountService.login(loginVo);
    }

    @GetMapping("/validate/token")
    public ResultEntity<String> validateToken() {
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

    @GetMapping("/validate/email")
    public ResultEntity<String> validateEmail(
            @RequestParam() String email) {

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

    @PostMapping("/validate/email-code")
    public ResultEntity<String> validateEmailCode(@Valid @RequestBody ValidateEmailCode validateEmailCode) {
        ValidateEmailCodeDTO validateEmailCodeDTO = new ValidateEmailCodeDTO();
        BeanUtils.copyProperties(validateEmailCode, validateEmailCodeDTO);
        try {
            return accountService.validateEmailCode(validateEmailCodeDTO);
        } catch (Exception exception) {
            return ResultEntity.serverError();
        }
    }

    @PostMapping("/register")
    public ResultEntity<String> register(@Valid @RequestBody RegisterVo registerVo) {
        try {
            String redisCode = redisTemplate.opsForValue()
                    .get(BlockConstants.REDIS_USER_REGISTER_VALIDATE_EMAIL + registerVo.getEmail());
            boolean result = registerVo.getCode().equals(redisCode);
            if (result) {
                if (registerVo.getPassword().equals(registerVo.getRepeatPassword())) {
                    return creatAccount(registerVo);
                }
                return ResultEntity.failure(HttpMessageConstants.PASSWORD_NOT_MATCH);
            } else {
                return ResultEntity.failure(HttpMessageConstants.VERIFICATION_CODE_EXPIRED);
            }
        } catch (DuplicateKeyException e) {
            return creatAccount(registerVo);
        } catch (Exception exception) {
            return ResultEntity.serverError();
        }
    }

    private ResultEntity<String> creatAccount(RegisterVo registerVo) {
        Account account = new UserAccount();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String id = snowflake.nextId() + "";
        account.setUsername(id);
        account.setPassword(encryptUtils.encryptPassword(registerVo.getPassword()));
        account.setEmail(registerVo.getEmail());
        account.setRole(RoleConstants.ROLE_USER);
        account.setCreatedAt(new Date());
        account.setIsBanned(false);
        return accountService.register(account);
    }

    @PostMapping("/forgot-password/send-code")
    public ResultEntity<String> forGotPasswordSendCode(@RequestParam String email) {
        try {
            accountService.sendCodeForgotPassword(email);
            return ResultEntity.success();
        } catch (ForbiddenException exception) {
            return ResultEntity.failure(exception.getMessage());
        } catch (RuntimeException exception) {
            return ResultEntity.failure(HttpMessageConstants.VERIFICATION_CODE_SEND_ERROR);
        }
    }

    @PostMapping("/forgot-password")
    public ResultEntity<String> forgotPassword(@Valid @RequestBody ForgotPassowrdVo forgotPassowrdVo) {
        try {
            String redisCode = redisTemplate.opsForValue()
                    .get(BlockConstants.REDIS_USER_FORGOTPASSWORD_CODE + forgotPassowrdVo.getEmail());
            boolean result = forgotPassowrdVo.getCode().equals(redisCode);
            if (result) {
                if (forgotPassowrdVo.getPassword().equals(forgotPassowrdVo.getRepeatPassword())) {
                    forgotPassowrdVo.setPassword(encryptUtils.encryptPassword(forgotPassowrdVo.getRepeatPassword()));
                    accountService.forgotPassword(forgotPassowrdVo);
                    return ResultEntity.success();
                }
                return ResultEntity.failure(HttpMessageConstants.PASSWORD_NOT_MATCH);
            }
            return ResultEntity.failure(HttpMessageConstants.VERIFICATION_CODE_EXPIRED);

        } catch (Exception exception) {
            return ResultEntity.serverError();
        }
    }

    @PostMapping("/logout")
    public ResultEntity<String> logout() {
        try {
            String jwt = jwtUtils.getToken();
            Integer id = jwtUtils.getId(jwt);
            Long expireTime = jwtUtils.getExpireTime(); // 过期时间(ms)
            // 向Redis中保存该用户的token，为黑名单
            redisTemplate.opsForValue().set(id + "_" + jwt, BlockConstants.REDIS_LOGOUT_BLOCK,
                    timeUtils.timestamp2Millis(expireTime), TimeUnit.MILLISECONDS);
            return ResultEntity.success();
        } catch (Exception e) {
            return ResultEntity.failure(e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResultEntity<String> test(@RequestParam(value = "username", defaultValue = "") String username) {
        return ResultEntity.success(username);
    }
}