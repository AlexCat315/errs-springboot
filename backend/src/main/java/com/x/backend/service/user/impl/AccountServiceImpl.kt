package com.x.backend.service.user.impl;

import com.x.backend.constants.HttpCodeConstants;
import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.constants.RoleConstants;
import com.x.backend.mapper.user.AccountMapper;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.user.entity.UserAccount;
import com.x.backend.pojo.user.vo.request.account.LoginVo;
import com.x.backend.service.user.AccountService;
import com.x.backend.util.EncryptUtils;
import com.x.backend.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("userAccountService")
@Service("userAccountService")
public class AccountServiceImpl implements AccountService {

    @Resource(name = "userAccountMapper")
    private AccountMapper accountMapper;

    @Resource
    private EncryptUtils encryptUtils;

    @Resource
    private JWTUtils<UserAccount> jwtUtils;

    @Override
    public ResultEntity<String> login(LoginVo loginVo) {
        UserAccount userAccount = accountMapper.findAccountByUsername(loginVo.getUsername());
        boolean verifyPassword = encryptUtils.verifyPassword(loginVo.getPassword(), userAccount.getPassword());
        if (!verifyPassword) {
            return ResultEntity.failure(HttpCodeConstants.BAD_REQUEST, HttpMessageConstants.ACCOUNT_OR_PASSWORD_ERROR);
        }
        // 验证是否被封禁
        if (Boolean.TRUE.equals(userAccount.isBanned())) {
            return ResultEntity.failure(HttpCodeConstants.FORBIDDEN, HttpMessageConstants.ACCOUNT_DISABLED);
        }
        // 验证权限
        if (userAccount.getRole() != RoleConstants.ROLE_USER) {
            return ResultEntity.failure(HttpCodeConstants.FORBIDDEN, HttpMessageConstants.ACCOUNT_NOT_ALLOWED_LOGIN);
        }
        String jwt = jwtUtils.createJWT(userAccount, 7);
        return ResultEntity.success(jwt);
    }
}