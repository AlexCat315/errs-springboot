package com.x.backend.controller.user;

import com.x.backend.constants.HttpMessageConstants;
import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.user.vo.request.account.LoginVo;
import com.x.backend.service.user.AccountService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userAccountController")
@RequestMapping("/api/user/account")
public class AccountController {

    @Resource(name = "userAccountService")
    private AccountService accountService;

    @PostMapping("/login")
    public ResultEntity<String> login(@RequestBody LoginVo loginVo) {
        if (loginVo == null || loginVo.getUsername() == null || loginVo.getPassword() == null
                || "".equals(loginVo.getUsername()) || "".equals(loginVo.getPassword())) {
            return ResultEntity.failure(HttpMessageConstants.ACCOUNT_OR_PASSWORD_ERROR);
        }
        return accountService.login(loginVo);
    }

}
