package com.x.backend.service.admin.impl;

import com.x.backend.mapper.admin.UserMapper;
import com.x.backend.pojo.admin.entity.Invite;
import com.x.backend.pojo.admin.vo.request.user.SearchAccountVO;
import com.x.backend.pojo.admin.vo.request.user.UpdateUserRoleVO;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.service.admin.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service("adminUserService")
public class UserServiceImpl implements UserService {

    @Resource(name = "adminUserMapper")
    private UserMapper userMapper;

    @Override
    public List<Account> getAll(PageSize pageSize) {
        List<Account> accounts = userMapper.getAll(pageSize);
        for (Account account : accounts) {
            account.setPassword("");
        }
        return accounts;
    }

    @Override
    public void updateUserRole(UpdateUserRoleVO updateUserRoleVO) {
        Integer i = userMapper.updateUserRole(updateUserRoleVO);
        if (i != 1) {
            throw new RuntimeException("更新用户角色失败");
        }
    }

    @Override
    public void resetPassword(Account account) {
        Integer i = userMapper.resetPassword(account);
        if (i != 1) {
            throw new RuntimeException("重置密码失败");
        }
    }

    @Override
    public void updateBanned(Account account) {
        Integer i = userMapper.updateBanned(account);
        if (i != 1)
            throw new RuntimeException("更新封禁状态失败");
    }

    @Override
    public Account getAccountById(Integer id) {
        return userMapper.getAccountById(id);
    }

    @Override
    public List<Account> searchInfo(SearchAccountVO searchAccountVO) {
        try {
            return userMapper.searchInfo(searchAccountVO);
        } catch (RuntimeException e) {
            log.error("RuntimeException{}", e.getMessage(), e);
            throw new RuntimeException("搜索失败");
        }
    }

    @Override
    public void updateImgUrl(Account account) {
        userMapper.updateImgUrl(account);
    }

    @Override
    public Invite getInviteByUserId(Integer id, Integer inviteId) {
        return userMapper.getInviteByUserId(id, inviteId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void updateInviteStatus(Invite invite) {
        Integer i = userMapper.updateInviteStatus(invite);
        if (i != 1) {
            throw new RuntimeException("更新邀请状态失败");
        }
    }

}
