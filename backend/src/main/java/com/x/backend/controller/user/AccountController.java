package com.x.backend.controller.user;

import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.user.vo.request.account.LoginVo;
import com.x.backend.service.user.AccountService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController("userAccountController")
@RequestMapping("/api/user/account")
public class AccountController {

    @Resource(name = "userAccountService")
    private AccountService accountService;

    @PostMapping("/login")
    public ResultEntity<String> login(@RequestBody LoginVo loginVo) {
        if (loginVo == null || loginVo.getUsername() == null || loginVo.getPassword() == null
                || loginVo.getUsername().isEmpty() || loginVo.getPassword().isEmpty()) {
            return ResultEntity.failure(HttpMessageConstants.INFO_INCOMPLETE);
        }
        return accountService.login(loginVo);
    }

    @GetMapping("/test")
    public ResultEntity<String> test(@RequestParam(value = "username", defaultValue = "") String username) {
        return ResultEntity.success(username);
    }
}