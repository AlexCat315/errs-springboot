package com.x.backend.controller.user

import com.x.backend.pojo.ResultEntity
import com.x.backend.pojo.user.User
import com.x.backend.service.user.AccountService
import jakarta.annotation.Resource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*


@RestController("userAccountController")
@RequestMapping("/api/user/account")
class AccountController {

    @Resource(name = "userAccountService")
    lateinit var accountService: AccountService


    @PostMapping("/register")
    fun register(@RequestBody user: User): ResultEntity<String>? {
        // 注册逻辑

        return ResultEntity.success()
    }

    @PostMapping("/login")
    fun login(@RequestBody user: User): ResultEntity<String>? {
        // 登录逻辑

        return ResultEntity.success()
    }


    @PostMapping("/post-test")
    fun test(@RequestParam string: String): ResultEntity<String>? {
        return ResultEntity.success("post function test success:$string")
    }

    @GetMapping("/get-test")
    fun getTest(@RequestParam string: String): ResultEntity<String>? {
        return ResultEntity.success("get function test success:$string")
    }
}