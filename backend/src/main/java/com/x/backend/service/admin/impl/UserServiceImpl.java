package com.x.backend.service.admin.impl;

import com.x.backend.mapper.admin.UserMapper;
import com.x.backend.pojo.admin.vo.request.user.UpdateUserRoleVO;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.service.admin.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
      Integer i =  userMapper.updateUserRole(updateUserRoleVO);
    }
}
