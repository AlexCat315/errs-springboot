package com.x.backend.controller.admin;

import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.BlockConstants;
import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.constants.RoleConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.admin.entity.AdminAccount;
import com.x.backend.pojo.admin.entity.Invite;
import com.x.backend.pojo.admin.vo.request.user.InviteVO;
import com.x.backend.pojo.admin.vo.request.user.ReviewVO;
import com.x.backend.pojo.admin.vo.request.user.SearchAccountVO;
import com.x.backend.pojo.admin.vo.request.user.UpdateUserRoleVO;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.common.PageSize;
import com.x.backend.service.admin.AccountService;
import com.x.backend.service.admin.UserService;
import com.x.backend.util.EncryptUtils;
import com.x.backend.util.JWTUtils;
import com.x.backend.util.TimeUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import com.x.backend.util.MinioUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RoleSecurity(value = { "admin" })
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
    @Resource(name = "adminAccountService")
    private AccountService accountService;
    @Resource
    private MinioUtils minioUtils;

    @Value("${minio.handler-pub-url}")
    private String pubHandlerUrl;

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
            redisTemplate.opsForValue().set(id + "_" + jwt, BlockConstants.REDIS_LOGOUT_BLOCK,
                    timeUtils.timestamp2Millis(expireTime), TimeUnit.MILLISECONDS);
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
                    redisTemplate.opsForValue().set(id + "_" + jwt, BlockConstants.REDIS_LOGOUT_BLOCK,
                            timeUtils.timestamp2Millis(expireTime), TimeUnit.MILLISECONDS);
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

    @PostMapping("/get/info")
    public ResultEntity<AdminAccount> getInfo() {
        try {
            Integer id = jwtUtils.getId();
            AdminAccount adminAccount = accountService.findById(id);
            if (adminAccount == null) {
                return ResultEntity.failure(HttpMessageConstants.LOGIN_EXPIRED);
            }
            adminAccount.setPassword("null");
            return ResultEntity.success(adminAccount);
        } catch (Exception e) {
            log.error("get info error: {}", e.getMessage());
            return ResultEntity.serverError();
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @PostMapping("/update/user/img")
    public ResultEntity<String> updateUserImg(@RequestParam("img") MultipartFile img) {
        try {
            // 上传封面图片到 MinIO
            String uploadImgUrlFile = minioUtils.pubUploadFile(img);
            uploadImgUrlFile = pubHandlerUrl + uploadImgUrlFile;
            // 更新用户头像
            Integer id = jwtUtils.getId();
            AdminAccount adminAccount = accountService.findById(id);
            if (adminAccount != null && adminAccount.getUserUrl() != null && !adminAccount.getUserUrl().isEmpty()) {
                minioUtils.pubDeleteFile(adminAccount.getUserUrl().replace(pubHandlerUrl, ""));
            }
            Account account = new Account();
            account.setAId(id);
            account.setUserUrl(uploadImgUrlFile);
            userService.updateImgUrl(account);
            return ResultEntity.success(uploadImgUrlFile);
        } catch (Exception e) {
            log.error("create book error: {}", e.getMessage(), e); // 详细记录异常堆栈
            return ResultEntity.failure(e.getMessage());
        }
    }

    // 用户审核
    @PostMapping("/update/user/review")
    public ResultEntity<String> updateUserReview(@RequestBody ReviewVO reviewVO) {
        try {
            Integer inviteId = jwtUtils.getId();
            Invite invite = userService.getInviteByUserId(reviewVO.getUserId(), inviteId);
            Date date = new Date();
            if (invite != null && invite.getStatus() == 0) {
                invite.setAId(reviewVO.getUserId());
                invite.setStatus(reviewVO.getStatus());
                invite.setResult(reviewVO.getResult());
                invite.setInvitedId(inviteId);
                invite.setHandleTime(date);
                userService.updateInviteStatus(invite);
                if (reviewVO.getResult().equals("通过")) {
                    accountService.updateBanned(reviewVO.getUserId(), false);
                } else {
                    accountService.updateBanned(reviewVO.getUserId(), true);
                }
                return ResultEntity.success("审核成功");
            }
            return ResultEntity.failure("审核失败，请稍后再试");
        } catch (RuntimeException exception) {
            log.info("update user review error: {}", exception.getMessage(), exception);
            return ResultEntity.failure(exception.getMessage());
        }
    }

    // 获取待审核用户列表
    @PostMapping("/get/user/review/list")
    public ResultEntity<List<InviteVO>> getUserReviewList() {
        try {
            Integer inviteId = jwtUtils.getId();
            return ResultEntity.success(userService.getInviteListByUserId(inviteId));
        } catch (RuntimeException exception) {
            log.info("get user review list error: {}", exception.getMessage(), exception);
            return ResultEntity.failure(exception.getMessage());
        }
    }

    @PostMapping("/get/user/review/history/list")
    public ResultEntity<List<InviteVO>> getUserReviewHistoryList() {
        try {
            Integer inviteId = jwtUtils.getId();
            return ResultEntity.success(userService.getInviteHistoryListByUserId(inviteId));
        } catch (RuntimeException exception) {
            log.info("get user review list error: {}", exception.getMessage(), exception);
            return ResultEntity.failure(exception.getMessage());
        }
    }

}
