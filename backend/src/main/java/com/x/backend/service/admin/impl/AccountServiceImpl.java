package com.x.backend.service.admin.impl;

import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.constants.InviteStatusConstants;
import com.x.backend.constants.RoleConstants;
import com.x.backend.exception.ForbiddenException;
import com.x.backend.mapper.admin.AccountMapper;
import com.x.backend.pojo.admin.dto.FindInviteCodeDTO;
import com.x.backend.pojo.admin.dto.ForgotPasswordDTO;
import com.x.backend.pojo.admin.dto.InsertInviteDTO;
import com.x.backend.pojo.admin.entity.Account;
import com.x.backend.pojo.admin.entity.Invite;
import com.x.backend.service.admin.AccountService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Override
    public Account login(String username) {
        // 调mapper方法
        Account result = accountMapper.login(username);
        if (result == null || result.getAId() < 1) {
            throw new RuntimeException(HttpMessageConstants.ACCOUNT_OR_PASSWORD_ERROR);
        }
        if (!result.getRole().equalsIgnoreCase(RoleConstants.ROLE_ADMIN)) {
            throw new ForbiddenException(HttpMessageConstants.FORBIDDEN);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setToken(String token, Account account) {
        // 调mapper方法，往数据库中更新token
        // 查询数据库中是否有该用户的记录，如果有，则更新token，如果没有，则新增记录
        Integer result = accountMapper.findTokenByAId(account.getAId());
        if (result == null || result < 1) {
            Integer insertResult = accountMapper.insertToken(account.getAId(), token);
            if (insertResult < 1) {
                throw new RuntimeException(HttpMessageConstants.INTERNAL_SERVER_ERROR);
            }
        } else {
            Integer updateResult = accountMapper.updateToken(token, account.getAId());
            if (updateResult < 1) {
                throw new RuntimeException(HttpMessageConstants.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public void findByEmail(String email) {
        // 调mapper方法
        // 查询数据库中是否有该用户的记录，如果有，则返回，如果没有，则返回null
        Integer result = accountMapper.findByEmail(email);
        if (result != null && result > 0) {
            throw new ForbiddenException(HttpMessageConstants.EMAIL_REGISTERED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer register(Account account) {
        // 调mapper方法
        // 往数据库中插入记录
        Integer result = accountMapper.insert(account);
        if (result < 1) {
            throw new RuntimeException(HttpMessageConstants.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @Override
    public void isEmailExists(String email) {
        // 调mapper方法
        // 查询数据库中是否有该用户的记录，如果有，则返回，如果没有，则返回null
        Integer result = accountMapper.findByEmail(email);
        if (result == null || result < 1) {
            throw new ForbiddenException(HttpMessageConstants.EMAIL_NOT_REGISTERED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePassword(Account account) {
        // 调mapper方法
        // 往数据库中更新密码
        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
        forgotPasswordDTO.setEmail(account.getEmail());
        forgotPasswordDTO.setNewPassword(account.getPassword());
        Integer result = accountMapper.updatePassword(forgotPasswordDTO);
        if (result != 1) {
            throw new ForbiddenException(HttpMessageConstants.ACCOUNT_PASSWORD_UPDATE_FAILED);
        }
    }

    @Override
    public Integer findByInviteCode(String inviteCode) {
        // 调mapper方法
        // 查询数据库中是否有此验证码
        FindInviteCodeDTO result = accountMapper.findByInviteCode(inviteCode);
        if (result == null || result.getAId() < 1) {
            throw new ForbiddenException(HttpMessageConstants.INVITATION_CODE_ERROR);
        }
        if (!result.getRole().equalsIgnoreCase(RoleConstants.ROLE_ADMIN)) {
            throw new ForbiddenException(HttpMessageConstants.INVITER_NO_PERMISSION);
        }
        return result.getAId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertInvite(InsertInviteDTO insertInviteDTO) {
        // 调mapper方法
        // 往数据库中插入记录
        Invite invite = new Invite();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(insertInviteDTO, invite);
        invite.setInviteTime(new Date());
        invite.setStatus(InviteStatusConstants.WAIT);
        Integer result = accountMapper.insertInvite(invite);
        if (result < 1) {
            throw new RuntimeException(HttpMessageConstants.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Account findAccountByNameOrEmail(String username) {
        // 调mapper方法
        // 查询数据库中是否有该用户的记录，如果有，则返回，如果没有，则返回null
        Account result = accountMapper.findAccountByNameOrEmail(username);
        if (result != null && result.getAId() > 0) {
            return result;
        }
        return null;
    }

}
