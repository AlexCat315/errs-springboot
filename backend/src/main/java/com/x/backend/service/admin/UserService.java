package com.x.backend.service.admin;

import com.x.backend.pojo.admin.vo.request.user.UpdateUserRoleVO;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.common.PageSize;

import java.util.List;

public interface UserService {
    List<Account> getAll(PageSize pageSize);

    void updateUserRole(UpdateUserRoleVO updateUserRoleVO);
}
