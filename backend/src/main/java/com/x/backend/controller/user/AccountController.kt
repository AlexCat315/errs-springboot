package com.x.backend.controller.user

import com.x.backend.pojo.ResultEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("userAccountController")
@RequestMapping("/api/user/account")
class AccountController {


    @PostMapping("/test")
    fun test() : ResultEntity<String> {
        return ResultEntity.success("test")
    }
}