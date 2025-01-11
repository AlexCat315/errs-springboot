package com.x.backend.mapper.user

import com.x.backend.pojo.admin.entity.Account
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
@Component("userAccountMapper")
@Mapper
interface AccountMapper {
    fun findUserByUsername(username:String):Account
}