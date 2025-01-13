package com.x.backend.service.user

import com.x.backend.pojo.ResultEntity
import com.x.backend.pojo.user.vo.request.account.LoginVo

interface AccountService {
    fun login(loginVo: LoginVo?): ResultEntity<String?>?
}
