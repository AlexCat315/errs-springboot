package com.x.backend.controller.admin;


import com.x.backend.annotation.RoleSecurity;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.admin.vo.request.user.UpdateUserRoleVO;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.service.admin.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RoleSecurity(value = {"admin"})
@RestController("adminUserController")
@RequestMapping("/api/admin/user")
public class UserController {


    @Resource(name = "adminUserService")
    private UserService userService;

    // 获取account的信息
    @PostMapping("/get/info/all")
    public ResultEntity<List<Account>> getAll(@RequestBody PageSize pageSize) {
        try {
            pageSize.setStart((pageSize.getPage() - 1) * pageSize.getSize());
            return ResultEntity.success(userService.getAll(pageSize));
        } catch (RuntimeException e) {
            log.error("RuntimeException{}", e.getMessage(), e);
            return ResultEntity.serverError();
        }

    }


    @PostMapping("/update/user/role")
    public ResultEntity<String> updateUserRole(@RequestBody UpdateUserRoleVO updateUserRoleVO) {
        try {
            userService.updateUserRole(updateUserRoleVO);
            return ResultEntity.success("更新成功");
        } catch (RuntimeException e) {
            log.error("RuntimeException{}", e.getMessage(), e);
            return ResultEntity.serverError();
        }
    }
}
