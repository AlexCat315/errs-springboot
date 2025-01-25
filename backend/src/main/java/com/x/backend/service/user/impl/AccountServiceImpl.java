package com.x.backend.service.user.impl;

import com.x.backend.constants.BlockConstants;
import com.x.backend.constants.HttpCodeConstants;
import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.constants.RoleConstants;
import com.x.backend.mapper.user.AccountMapper;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.user.entity.UserAccount;
import com.x.backend.pojo.user.vo.request.account.LoginVo;
import com.x.backend.service.admin.EmailService;
import com.x.backend.service.user.AccountService;
import com.x.backend.util.EncryptUtils;
import com.x.backend.util.JWTUtils;
import com.x.backend.util.RandomCodeGeneratorUtils;
import com.x.backend.util.TimeUtils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Component("userAccountService")
@Service("userAccountService")
public class AccountServiceImpl implements AccountService {

    @Resource(name = "userAccountMapper")
    private AccountMapper accountMapper;

    @Resource
    private EncryptUtils encryptUtils;

    @Resource
    private JWTUtils<UserAccount> jwtUtils;

    @Resource
    private EmailService emailService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private RandomCodeGeneratorUtils randomCodeGeneratorUtils;
    @Resource
    private TimeUtils timeUtils;

    @Override
    public ResultEntity<String> login(LoginVo loginVo) {
        UserAccount userAccount = accountMapper.findAccountByUsername(loginVo.getUsername());
        if (userAccount == null) {
            return ResultEntity.failure(HttpCodeConstants.BAD_REQUEST, HttpMessageConstants.ACCOUNT_NOT_EXIST);
        }
        boolean verifyPassword = encryptUtils.verifyPassword(loginVo.getPassword(), userAccount.getPassword());
        if (!verifyPassword) {
            return ResultEntity.failure(HttpCodeConstants.BAD_REQUEST, HttpMessageConstants.ACCOUNT_OR_PASSWORD_ERROR);
        }
        // 验证是否被封禁
        if (userAccount.getIsBanned()) {
            return ResultEntity.failure(HttpCodeConstants.FORBIDDEN, HttpMessageConstants.ACCOUNT_DISABLED);
        }
        // 验证权限
        if (!Objects.equals(userAccount.getRole(), RoleConstants.ROLE_USER)) {
            return ResultEntity.failure(HttpCodeConstants.FORBIDDEN, HttpMessageConstants.ACCOUNT_NOT_ALLOWED_LOGIN);
        }
        String jwt = jwtUtils.createJWT(userAccount, 7);
        return ResultEntity.success(jwt);
    }

    @Override
    public ResultEntity<String> validateEmail(String email) {
        String key = BlockConstants.USER_REGISTER_VALIDATE_EMAIL + email;
        Long expire = redisTemplate.getExpire(key, java.util.concurrent.TimeUnit.SECONDS);
        if (expire <= 0 || expire <= 520) {
            Integer reslut = accountMapper.validateEmail(email);
            if (reslut == null || reslut <= 0) {
                // 生成随机6位字符验证码
                String code = randomCodeGeneratorUtils.generateRandomCode();
                // 向email服务发送验证邮件
                emailService.sendEmail(email, "验证邮箱", "欢迎注册我们的网站，您的验证码为：" + code + "，请在10分钟内完成验证。");
                redisTemplate.opsForValue().set(key, code, 600, java.util.concurrent.TimeUnit.SECONDS);
                return ResultEntity.success();
            }
        } else {
            return ResultEntity.failure(HttpMessageConstants.REQUEST_FREQUENT);
        }
        return ResultEntity.failure(HttpMessageConstants.EMAIL_REGISTERED);
    }
}