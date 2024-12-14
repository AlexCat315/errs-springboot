package com.x.backend.service.impl;

import com.x.backend.exception.ForbiddenException;
import com.x.backend.mapper.AccountMapper;
import com.x.backend.pojo.dto.AccountDTO;
import com.x.backend.pojo.dto.ForgotPasswordDTO;
import com.x.backend.pojo.entity.Account;
import com.x.backend.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Override
    public Account login(AccountDTO accountDTO) {
        // 调mapper方法
        Account result = accountMapper.login(accountDTO);
        if (result == null || result.getAId() < 1) {
            throw new RuntimeException("账号或密码错误");
        }
        return result;
    }

    @Override
    public void setToken(String token, Account account) {
        // 调mapper方法，往数据库中更新token
        // 查询数据库中是否有该用户的记录，如果有，则更新token，如果没有，则新增记录
        Integer result = accountMapper.findTokenByAId(account.getAId());
        if (result == null || result < 1) {
            Integer insertResult = accountMapper.insertToken(account.getAId(), token);
            if (insertResult < 1) {
                throw new RuntimeException("服务器内部错误");
            }

        } else {
            Integer updateResult = accountMapper.updateToken(token, account.getAId());
            if (updateResult < 1) {
                throw new RuntimeException("服务器内部错误");
            }
        }
    }

    @Override
    public void findByEmail(String email) {
        // 调mapper方法
        // 查询数据库中是否有该用户的记录，如果有，则返回，如果没有，则返回null
        Integer result = accountMapper.findByEmail(email);
        if (result!= null && result > 0) {
            throw new ForbiddenException("该邮箱已被注册");
        }
    }

    @Override
    public void register(Account account) {
        // 调mapper方法
        // 往数据库中插入记录
        Integer result = accountMapper.insert(account);
        if (result < 1) {
            throw new RuntimeException("服务器内部错误");
        }
    }

    @Override
    public void isEmailExists(String email) {
        // 调mapper方法
        // 查询数据库中是否有该用户的记录，如果有，则返回，如果没有，则返回null
        Integer result = accountMapper.findByEmail(email);
        if (result == null || result < 1) {
            throw new ForbiddenException("该邮箱未注册");
        }
    }

    @Override
    public void updatePassword(Account account) {
        // 调mapper方法
        // 往数据库中更新密码
        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
        forgotPasswordDTO.setEmail(account.getEmail());
        forgotPasswordDTO.setNewPassword(account.getPassword());
        Integer result = accountMapper.updatePassword(forgotPasswordDTO);
        if (result != 1) {
            throw new ForbiddenException("密码更新失败");
        }
    }

}
