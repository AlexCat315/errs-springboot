package com.x.backend.service.user.impl

import com.x.backend.mapper.user.AccountMapper
import com.x.backend.pojo.admin.entity.Account
import com.x.backend.service.user.AccountService
import jakarta.annotation.Resource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component("userAccountService")
@Service("userAccountService")
class AccountServiceImpl() : AccountService {

  @Resource(name = "userAccountMapper") lateinit var accountMapper: AccountMapper

  override fun findUsreByUsername(username: String): Account {
    val user: Account = accountMapper.findUserByUsername(username)
    return user
  }
}
