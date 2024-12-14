package com.x.backend.controller;

import com.x.backend.exception.ForbiddenException;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.dto.AccountDTO;
import com.x.backend.pojo.entity.Account;
import com.x.backend.pojo.vo.request.ForgotPasswordVo;
import com.x.backend.pojo.vo.request.LoginVo;
import com.x.backend.pojo.vo.request.RegisterVo;
import com.x.backend.service.AccountService;
import com.x.backend.service.EmailService;
import com.x.backend.util.JWTUtils;
import com.x.backend.util.RandomCodeGeneratorUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j

@RestController
@RequestMapping(value = "/api/account")
public class AccountController {

    @Resource
    private AccountService accountService;
    @Resource
    private JWTUtils jwtUtils;
    @Resource
    private EmailService emailService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private RandomCodeGeneratorUtils randomCodeGeneratorUtils;

    @PostMapping("/login")
    public ResultEntity<String> login(@RequestBody LoginVo loginVo) {
        if (loginVo.getPassword() == null) {
            return ResultEntity.failure(-1, "密码不能为空");
        }
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(loginVo.getUsername());
        accountDTO.setPassword(loginVo.getPassword());
        try {
            Account account = accountService.login(accountDTO);
            String jwt = jwtUtils.createJWT(account, 7);
            if (loginVo.getRememberMe()) {
                return ResultEntity.success("localStorage_" + jwt);
            } else {
                jwt = jwtUtils.createJWT(account, 1);
                return ResultEntity.success("sessionStorage_" + jwt);
            }
        } catch (RuntimeException e) {
            return ResultEntity.failure(-1, e.getMessage());
        }
    }

    @PostMapping(value = "/validate-email-register")
    public ResultEntity<String> validateEmailRegister(@RequestParam(value = "email") String email) {
        if (email == null) {
            return ResultEntity.failure(-1, "邮箱不能为空");
        }
        String redisEmail = redisTemplate.opsForValue().get(email);
        Long expire = redisTemplate.getExpire(email, java.util.concurrent.TimeUnit.SECONDS);
        if (redisEmail != null && expire != null && expire > 540) {
            return ResultEntity.failure(-1, "该邮箱已请求过验证，请稍后再试");
        }
        // 向数据库查询该邮箱是否已经注册过
        try {
            accountService.findByEmail(email);
            // 生成随机6位字符验证码
            String code = randomCodeGeneratorUtils.generateRandomCode();
            // 向email服务发送验证邮件
            emailService.sendEmail(email, "验证邮箱", "欢迎注册我们的网站，您的验证码为：" + code + "，请在10分钟内完成验证。");
            // 将验证码存入redis，以便校验
            redisTemplate.opsForValue().set(email, code, 600, java.util.concurrent.TimeUnit.SECONDS);
            return ResultEntity.success();
        } catch (ForbiddenException e) {
            return ResultEntity.failure(-1, e.getMessage());
        }
    }

    @PostMapping(value = "/register")
    public ResultEntity<String> register(@RequestBody RegisterVo registerVo) {
        if (registerVo.getUsername() == null || registerVo.getPassword() == null ||
                registerVo.getRepeatPassword() == null || registerVo.getEmail() == null
                || registerVo.getCode() == null) {
            return ResultEntity.failure(-1, "请填写完整信息");
        }
        if (!registerVo.getPassword().equals(registerVo.getRepeatPassword())) {
            return ResultEntity.failure(-1, "两次密码输入不一致");
        }
        // 验证验证码是否正确
        String code = redisTemplate.opsForValue().get(registerVo.getEmail());
        if (code == null || !code.equals(registerVo.getCode())) {
            return ResultEntity.failure(-1, "验证码错误");
        }
        // 向数据库插入用户信息
        Account account = new Account();
        account.setUsername(registerVo.getUsername());
        account.setPassword(registerVo.getPassword());
        account.setEmail(registerVo.getEmail());
        account.setRole("user");
        try {
            accountService.register(account);
            // 清除验证码
            redisTemplate.delete(registerVo.getEmail());
            return ResultEntity.success();
        } catch (RuntimeException e) {
            return ResultEntity.failure(-1, e.getMessage());
        }
    }

    @PostMapping(value = "validate-email-forgot-password")
    public ResultEntity<String> validateEmailForgotPassword(@RequestParam(value = "email") String email) {
        if (email == null) {
            return ResultEntity.failure(-1, "邮箱不能为空");
        }
        String redisEmail = redisTemplate.opsForValue().get(email);
        Long expire = redisTemplate.getExpire(email, java.util.concurrent.TimeUnit.SECONDS);
        if (redisEmail != null && expire != null && expire > 540) {
            return ResultEntity.failure(-1, "该邮箱已请求过验证，请稍后再试");
        }
        // 向数据库查询该邮箱是否已经注册过
        try {
            accountService.isEmailExists(email);
            // 生成随机6位字符验证码
            String code = randomCodeGeneratorUtils.generateRandomCode();
            // 向email服务发送验证邮件
            emailService.sendEmail(email, "验证邮箱", "您正在重置密码，您的验证码为：" + code + "，请在10分钟内完成验证。");
            // 将验证码存入redis，以便校验
            redisTemplate.opsForValue().set(email, code, 600, java.util.concurrent.TimeUnit.SECONDS);
            return ResultEntity.success();
        } catch (ForbiddenException e) {
            return ResultEntity.failure(-1, e.getMessage());
        }
    }

    @PostMapping(value = "/forgot-password")
    public ResultEntity<String> forgotPassword(@RequestBody ForgotPasswordVo forgotPasswordVo) {
        if (forgotPasswordVo.getPassword() == null || forgotPasswordVo.getConfirmPassword() == null ||
                forgotPasswordVo.getCode() == null) {
            return ResultEntity.failure(-1, "请填写完整信息");
        }
        if (!forgotPasswordVo.getPassword().equals(forgotPasswordVo.getConfirmPassword())) {
            return ResultEntity.failure(-1, "两次密码输入不一致");
        }
        // 验证验证码是否正确
        String code = redisTemplate.opsForValue().get(forgotPasswordVo.getEmail());
        if (code == null || !code.equals(forgotPasswordVo.getCode())) {
            return ResultEntity.failure(-1, "验证码错误");
        }
        // 向数据库更新用户密码
        Account account = new Account();
        account.setPassword(forgotPasswordVo.getPassword());
        account.setEmail(forgotPasswordVo.getEmail());
        try {
            accountService.updatePassword(account);
            // 清除验证码
            redisTemplate.delete(forgotPasswordVo.getEmail());
            return ResultEntity.success();
        } catch (RuntimeException e) {
            return ResultEntity.failure(-1, e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResultEntity<String> logout(@RequestHeader(value = "Authorization") String token) {
        try {
            String jwt = jwtUtils.getToken(token);
            Integer id = jwtUtils.getId(jwt);
            log.warn("logout user id: {}", id);
            // 向Redis中保存该用户的token，为黑名单
            redisTemplate.opsForValue().set("blacklist" + id, jwt, 7, TimeUnit.DAYS);
            return ResultEntity.success();
        } catch (Exception e) {
            return ResultEntity.failure(-1, e.getMessage());
        }


    }

}
