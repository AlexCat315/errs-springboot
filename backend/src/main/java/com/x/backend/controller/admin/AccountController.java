package com.x.backend.controller.admin;

import com.x.backend.constants.BlockConstants;
import com.x.backend.constants.HttpCodeConstants;
import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.constants.RoleConstants;
import com.x.backend.exception.ForbiddenException;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.admin.dto.InsertInviteDTO;
import com.x.backend.pojo.admin.entity.Account;
import com.x.backend.pojo.admin.vo.request.ForgotPasswordVo;
import com.x.backend.pojo.admin.vo.request.LoginVo;
import com.x.backend.pojo.admin.vo.request.RegisterVo;
import com.x.backend.service.admin.AccountService;
import com.x.backend.service.admin.EmailService;
import com.x.backend.util.EncryptUtils;
import com.x.backend.util.JWTUtils;
import com.x.backend.util.RandomCodeGeneratorUtils;
import com.x.backend.util.TimeUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 此路径下的接口不需要权限控制，因为不需要登录即可访问，仅仅是一些基础的操作，如注册、登录、忘记密码等。
 * 如果需要修改@RequestMapping路径，请修改/backend/src/main/resources/exclude-paths.json中的配置。
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/admin/account")
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
    @Resource
    private TimeUtils timeUtils;
    @Resource
    private EncryptUtils encryptUtils;

    @PostMapping("/login")
    public ResultEntity<String> login(@RequestBody LoginVo loginVo) {
        if (loginVo.getPassword() == null) {
            return ResultEntity.failure(HttpCodeConstants.BAD_REQUEST_PARAM,
                    HttpMessageConstants.PASSWORD_NOT_NULL);
        }
        try {
            Account account = accountService.login(loginVo.getUsername());
            boolean verifyPassword = encryptUtils.verifyPassword(loginVo.getPassword(), account.getPassword());
            if (!verifyPassword) {
                return ResultEntity.failure(HttpCodeConstants.BAD_REQUEST, HttpMessageConstants.PASSWORD_ERROR);
            }
            // 验证是否被禁止登录
            if (!account.getIsAllowLogin()) {
                return ResultEntity.failure(HttpCodeConstants.FORBIDDEN, HttpMessageConstants.ACCOUNT_NOT_ALLOWED_LOGIN);
            }
            // 验证是否被封禁
            if (account.getIsBanned()) {
                return ResultEntity.failure(HttpCodeConstants.FORBIDDEN, HttpMessageConstants.ACCOUNT_DISABLED);
            }
            String jwt = jwtUtils.createJWT(account, 7);
            if (loginVo.getRememberMe()) {
                return ResultEntity.success("localStorage_" + jwt);
            } else {
                jwt = jwtUtils.createJWT(account, 1);
                return ResultEntity.success("sessionStorage_" + jwt);
            }
        } catch (RuntimeException e) {
            return ResultEntity.failure(HttpCodeConstants.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(value = "/validate-email-register")
    public ResultEntity<String> validateEmailRegister(@RequestParam String email) {
        if (email == null) {
            return ResultEntity.failure(HttpCodeConstants.BAD_REQUEST, HttpMessageConstants.EMAIL_NOT_NULL);
        }
        String redisEmail = redisTemplate.opsForValue().get(email);
        Long expire = redisTemplate.getExpire(email, java.util.concurrent.TimeUnit.SECONDS);
        if (redisEmail != null && expire > 540) {
            return ResultEntity.failure(HttpCodeConstants.BAD_REQUEST, HttpMessageConstants.REQUEST_FREQUENT);
        }
        try {
            // 向数据库查询该邮箱是否已经注册过
            accountService.findByEmail(email);
            // 生成随机6位字符验证码
            String code = randomCodeGeneratorUtils.generateRandomCode();
            // 向email服务发送验证邮件
            emailService.sendEmail(email, "验证邮箱", "欢迎注册我们的网站，您的验证码为：" + code + "，请在10分钟内完成验证。");
            // 将验证码存入redis，以便校验
            redisTemplate.opsForValue().set(email, code, 600, java.util.concurrent.TimeUnit.SECONDS);
            return ResultEntity.success();
        } catch (ForbiddenException e) {
            return ResultEntity.failure(HttpCodeConstants.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping(value = "/register")
    @Transactional(rollbackFor = RuntimeException.class)
    public ResultEntity<String> register(@RequestBody RegisterVo registerVo) {
        if (registerVo.getInviteCode() == null) {
            return ResultEntity.failure(HttpMessageConstants.INVITATION_CODE_NOT_NULL);
        }
        if (registerVo.getUsername() == null || registerVo.getPassword() == null ||
                registerVo.getRepeatPassword() == null || registerVo.getEmail() == null
                || registerVo.getCode() == null) {
            return ResultEntity.failure(HttpMessageConstants.INFO_INCOMPLETE);
        }
        if (!registerVo.getPassword().equals(registerVo.getRepeatPassword())) {
            return ResultEntity.failure(HttpMessageConstants.PASSWORD_NOT_MATCH);
        }
        // 验证用户名是否存在
        try {
            accountService.isUsernameExists(registerVo.getUsername());
        } catch (ForbiddenException e) {
            return ResultEntity.failure(e.getMessage());
        }
        // 验证验证码是否正确
        String code = redisTemplate.opsForValue().get(registerVo.getEmail());
        if (code == null || !code.equals(registerVo.getCode())) {
            return ResultEntity.failure(HttpMessageConstants.VERIFICATION_CODE_EXPIRED);
        }
        // 验证邀请码是否正确
        Integer inviteID;
        try {
            inviteID = accountService.findByInviteCode(registerVo.getInviteCode());
        } catch (ForbiddenException e) {
            return ResultEntity.failure(e.getMessage());
        }
        if (inviteID == null) {
            return ResultEntity.failure(HttpMessageConstants.INVITATION_CODE_ERROR);
        }

        // 向数据库插入用户信息
        Account account = new Account();
        account.setUsername(registerVo.getUsername());
        account.setPassword(encryptUtils.encryptPassword(registerVo.getPassword()));
        account.setEmail(registerVo.getEmail());
        account.setRole(RoleConstants.ROLE_ADMIN);
        account.setCreatedAt(new Date());
        account.setIsBanned(true);
        account.setIsAllowLogin(false);
        try {
            Integer aId = accountService.register(account);
            // 清除验证码
            redisTemplate.delete(registerVo.getEmail());
            // 向数据库插入邀请信息
            InsertInviteDTO insertInviteDTO = new InsertInviteDTO();
            insertInviteDTO.setAId(aId);
            insertInviteDTO.setInvitedId(inviteID);
            accountService.insertInvite(insertInviteDTO);
            return ResultEntity.success();
        } catch (RuntimeException e) {
            return ResultEntity.failure(HttpMessageConstants.REGISTER_FAILED);
        }
    }

    @PostMapping(value = "validate-email-forgot-password")
    public ResultEntity<String> validateEmailForgotPassword(@RequestParam String email) {
        if (email == null) {
            return ResultEntity.failure(HttpMessageConstants.EMAIL_ERROR);
        }
        String redisEmail = redisTemplate.opsForValue().get(email);
        Long expire = redisTemplate.getExpire(email, java.util.concurrent.TimeUnit.SECONDS);
        if (redisEmail != null && expire > 540) {
            return ResultEntity.failure(HttpMessageConstants.REQUEST_FREQUENT);
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
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping(value = "/forgot-password")
    public ResultEntity<String> forgotPassword(@RequestBody ForgotPasswordVo forgotPasswordVo) {
        if (forgotPasswordVo.getPassword() == null || forgotPasswordVo.getConfirmPassword() == null ||
                forgotPasswordVo.getCode() == null) {
            return ResultEntity.failure(HttpMessageConstants.INFO_INCOMPLETE);
        }
        if (!forgotPasswordVo.getPassword().equals(forgotPasswordVo.getConfirmPassword())) {
            return ResultEntity.failure(HttpMessageConstants.PASSWORD_NOT_MATCH);
        }
        // 验证验证码是否正确
        String code = redisTemplate.opsForValue().get(forgotPasswordVo.getEmail());
        if (code == null || !code.equals(forgotPasswordVo.getCode())) {
            return ResultEntity.failure(HttpMessageConstants.VERIFICATION_CODE_EXPIRED);
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
            return ResultEntity.failure(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResultEntity<String> logout() {
        try {
            String jwt = jwtUtils.getToken();
            Integer id = jwtUtils.getId(jwt);
            Long expireTime = jwtUtils.getExpireTime(); // 过期时间(ms)
            // 向Redis中保存该用户的token，为黑名单
            redisTemplate.opsForValue().set(id + "_" + jwt, BlockConstants.REDIS_LOGOUT_BLOCK, timeUtils.timestamp2Millis(expireTime), TimeUnit.MILLISECONDS);
            return ResultEntity.success();
        } catch (Exception e) {
            return ResultEntity.failure(e.getMessage());
        }
    }

    /**
     * 验证token是否有效
     */
    @PostMapping("/validate-token")
    public ResultEntity<String> validateToken() {
        try {
            boolean verifyToken = jwtUtils.verifyToken();
            if (verifyToken) {
                return ResultEntity.success();
            }
            return ResultEntity.failure(HttpMessageConstants.LOGIN_EXPIRED);
        } catch (Exception e) {
            log.error("validate token error: {}", e.getMessage());
            return ResultEntity.failure(HttpMessageConstants.TOKEN_CHECK_FAILED);
        }
    }


}
