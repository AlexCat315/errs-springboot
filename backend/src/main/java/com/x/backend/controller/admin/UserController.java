package com.x.backend.controller.admin;


import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.BlockConstants;
import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.admin.entity.AdminAccount;
import com.x.backend.pojo.admin.vo.request.user.SearchAccountVO;
import com.x.backend.pojo.admin.vo.request.user.UpdateUserRoleVO;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.service.admin.UserService;
import com.x.backend.util.EncryptUtils;
import com.x.backend.util.JWTUtils;
import com.x.backend.util.TimeUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RoleSecurity(value = {"admin"})
@RestController("adminUserController")
@RequestMapping("/api/admin/user")
public class UserController {


    @Resource(name = "adminUserService")
    private UserService userService;
    @Resource
    private EncryptUtils encryptUtils;
    @Resource
    private JWTUtils<AdminAccount> jwtUtils;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private TimeUtils timeUtils;

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
            // 将此用户的token拉黑
            String jwt = jwtUtils.getToken();
            Long expireTime = jwtUtils.getExpireTime();
            Integer id = jwtUtils.getId();
            redisTemplate.opsForValue().set(id + "_" + jwt, BlockConstants.REDIS_LOGOUT_BLOCK, timeUtils.timestamp2Millis(expireTime), TimeUnit.MILLISECONDS);
            return ResultEntity.success("更新成功");
        } catch (RuntimeException e) {
            log.error("RuntimeException{}", e.getMessage(), e);
            return ResultEntity.serverError();
        }
    }

    /**
     * 重置密码
     * 默认123456
     */
    @PostMapping("/update/reset-password")
    public ResultEntity<String> resetPassword(@RequestParam("userId") Integer userId) {
        try {
            Account account = new Account();
            account.setAId(userId);
            account.setPassword(encryptUtils.encryptPassword("123456"));
            userService.resetPassword(account);
            return ResultEntity.success("重置密码成功");
        } catch (RuntimeException e) {
            log.error("RuntimeException{}", e.getMessage(), e);
            return ResultEntity.serverError();
        }
    }

    /**
     * 封禁账户
     */
    @PostMapping("/update/ban-account")
    public ResultEntity<String> banAccount(@RequestParam("userId") Integer userId) {
        try {
            Account account = new Account();
            account.setAId(userId);
            account.setIsBanned(true);
            userService.updateBanned(account);
            // 拉黑token
            boolean verifyToken = jwtUtils.verifyToken() && RoleConstants.ROLE_ADMIN.equals(jwtUtils.getRole());
            if (verifyToken) {
                Integer id = jwtUtils.getId();
                // 查询账户封禁状态
                account = userService.getAccountById(id);
                if (account != null && account.getIsBanned()) {
                    // 拉黑此token
                    String jwt = jwtUtils.getToken();
                    Long expireTime = jwtUtils.getExpireTime();
                    redisTemplate.opsForValue().set(id + "_" + jwt, BlockConstants.REDIS_LOGOUT_BLOCK, timeUtils.timestamp2Millis(expireTime), TimeUnit.MILLISECONDS);
                    return ResultEntity.failure(HttpMessageConstants.ACCOUNT_DISABLED);
                }
            }
            return ResultEntity.success("封禁成功");
        } catch (RuntimeException e) {
            log.error("RuntimeException{}", e.getMessage(), e);
            return ResultEntity.serverError();
        }
    }

    /**
     * 解封账户
     */
    @PostMapping("/update/unban-account")
    public ResultEntity<String> unbanAccount(@RequestParam("userId") Integer userId) {
        try {
            Account account = new Account();
            account.setAId(userId);
            account.setIsBanned(false);
            userService.updateBanned(account);
            return ResultEntity.success("解封成功");
        } catch (RuntimeException e) {
            log.error("RuntimeException{}", e.getMessage(), e);
            return ResultEntity.serverError();
        }
    }

    @PostMapping("/search/info")
    public ResultEntity<List<Account>> searchInfo(@RequestBody SearchAccountVO searchAccountVO) {
        try {
            searchAccountVO.setStart((searchAccountVO.getPage() - 1) * searchAccountVO.getSize());
            return ResultEntity.success(userService.searchInfo(searchAccountVO));
        } catch (RuntimeException e) {
            log.error("RuntimeException{}", e.getMessage(), e);
            return ResultEntity.serverError();
        }
    }

}
