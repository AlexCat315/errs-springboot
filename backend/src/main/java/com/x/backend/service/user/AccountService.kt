package com.x.backend.service.user

import com.x.backend.pojo.admin.entity.Account
import org.springframework.stereotype.Service

interface AccountService {
    /**
     * 通过用户名查询用户信息
     * @param username
     * @return Account
     */
     fun findUsreByUsername(username: String): Account

}