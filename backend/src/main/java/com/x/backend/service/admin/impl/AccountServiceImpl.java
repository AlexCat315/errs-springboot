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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component("adminAccountService")
@Service("adminAccountService")
public class AccountServiceImpl implements AccountService {

    @Resource(name = "adminAccountMapper")
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


    @Override
    public void findByEmail(String email) {
        // 调mapper方法
        // 查询数据库中是否有该用户的记录，如果有，则返回，如果没有，则返回null
        Integer result = accountMapper.findByEmail(email);
        if (result != null && result > 0) {
            throw new ForbiddenException(HttpMessageConstants.EMAIL_REGISTERED);
        }
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

    @Transactional(rollbackFor = RuntimeException.class)
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
        if (result == null || result.getAId() == null || result.getAId() < 1) {
            throw new ForbiddenException(HttpMessageConstants.INVITATION_CODE_ERROR);
        }
        if (!result.getRole().equalsIgnoreCase(RoleConstants.ROLE_ADMIN)) {
            throw new ForbiddenException(HttpMessageConstants.INVITER_NO_PERMISSION);
        }
        return result.getAId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Integer register(Account account) {
        // 调mapper方法
        // 往数据库中插入记录
        try {
            Integer result = accountMapper.insert(account);
            if (result < 1) {
                throw new RuntimeException(HttpMessageConstants.INTERNAL_SERVER_ERROR);
            }
            return result;
        } catch (RuntimeException e) {
            throw new RuntimeException(HttpMessageConstants.REGISTER_FAILED);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void insertInvite(InsertInviteDTO insertInviteDTO) {
        // 调mapper方法
        // 往数据库中插入记录
        Invite invite = new Invite();
        BeanUtils.copyProperties(insertInviteDTO, invite);
        invite.setInviteTime(new Date());
        invite.setStatus(InviteStatusConstants.WAIT);
        try {
            Integer result = accountMapper.insertInvite(invite);
            if (result < 1) {
                throw new RuntimeException(HttpMessageConstants.INTERNAL_SERVER_ERROR);
            }
        } catch (RuntimeException e) {
            if (e.getMessage().contains(HttpMessageConstants.INVITATION_CODE_ERROR)) {
                throw new ForbiddenException(HttpMessageConstants.REGISTER_FAILED);
            }
            throw new RuntimeException(HttpMessageConstants.REGISTER_FAILED);
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

    @Override
    public void isUsernameExists(String username) {
        // 调mapper方法
        // 查询数据库中是否有该用户的记录，如果有，则返回，如果没有，则返回null
        Account result = accountMapper.findAccountByName(username);
        if (result != null && result.getAId() > 0) {
            throw new ForbiddenException(HttpMessageConstants.ACCOUNT_REGISTERED);
        }
    }

    @Override
    public Account findById(Integer id) {
        // 调mapper方法
        // 查询数据库中是否有该用户的记录，如果有，则返回，如果没有，则返回null
        Account result = accountMapper.findById(id);
        if (result != null && result.getAId() > 0) {
            return result;
        }
        return null;
    }

}
