package com.x.backend.mapper.user;

import com.x.backend.pojo.user.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Component(value = "userAccountMapper")
@Mapper
public interface AccountMapper {
    UserAccount findAccountByUsername(String username);
}
