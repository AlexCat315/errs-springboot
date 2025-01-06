package com.x.backend.controller.user

import com.x.backend.pojo.ResultEntity
import org.springframework.web.bind.annotation.*

@RestController(value = "userAccountController")
@RequestMapping("/api/user/account")
class AccountController {

    @PostMapping("/register")
    fun register(@RequestBody user: String): ResultEntity<String> {

        return ResultEntity.success(user)
    }


    @PostMapping("/post-test")
    fun test(@RequestParam string:  String ) : ResultEntity<String> {
        return ResultEntity.success("post function test success:$string")
    }

    @GetMapping("/get-test")
    fun getTest(@RequestParam string: String) : ResultEntity<String> {
        return ResultEntity.success("get function test success:$string")
    }
}