package com.x.backend.controller.user

import com.x.backend.constants.HttpMessageConstants
import com.x.backend.pojo.ResultEntity
import com.x.backend.pojo.user.vo.request.account.LoginVo
import com.x.backend.service.user.AccountService
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("userAccountController")
@RequestMapping("/api/user/account")
class AccountController {
    @Resource(name = "userAccountService")
    private val accountService: AccountService? = null

    @PostMapping("/login")
    fun login(@RequestBody loginVo: LoginVo?): ResultEntity<String?>? {
        if (loginVo == null || loginVo.username == null || loginVo.password == null || loginVo.username.isEmpty() || loginVo.password.isEmpty()) {
            return ResultEntity.failure(HttpMessageConstants.ACCOUNT_OR_PASSWORD_ERROR)
        }
        return accountService!!.login(loginVo)
    }
}
