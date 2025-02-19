package com.x.backend.mapper.admin;

import com.x.backend.pojo.admin.vo.request.user.UpdateUserRoleVO;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.common.PageSize;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("adminUserMapper")
public interface UserMapper {
    List<Account> getAll(PageSize pageSize);

    Integer updateUserRole(UpdateUserRoleVO updateUserRoleVO);
}
