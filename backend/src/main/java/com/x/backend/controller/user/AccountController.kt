package com.x.backend.controller.user

import com.x.backend.pojo.ResultEntity
import org.springframework.web.bind.annotation.*

@RestController(value = "userAccountController")
@RequestMapping("/api/user/account")
class AccountController {


    @PostMapping("/post-test")
    fun test(@RequestParam string:  String ) : ResultEntity<String> {
        return ResultEntity.success(string)
    }

    @GetMapping("/get-test")
    fun getTest() : ResultEntity<String> {
        return ResultEntity.success("get success")
    }
}