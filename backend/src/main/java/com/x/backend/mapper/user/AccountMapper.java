package com.x.backend.mapper.user;


import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.user.dto.account.ForgotPasswordDTO;
import com.x.backend.pojo.user.entity.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component(value = "userAccountMapper")
@Mapper
public interface AccountMapper {
    UserAccount findAccountByUsername(String username);

    Integer validateEmail(String email);

    Integer register(Account account);

    void forgotPassword(ForgotPasswordDTO forgotPasswordDTO);

}
