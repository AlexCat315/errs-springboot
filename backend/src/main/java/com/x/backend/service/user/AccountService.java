package com.x.backend.service.user;

import com.x.backend.pojo.ResultEntity;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.user.dto.account.ValidateEmailCodeDTO;
import com.x.backend.pojo.user.vo.request.account.ForgotPassowrdVo;
import com.x.backend.pojo.user.vo.request.account.LoginVo;


public interface AccountService {
    ResultEntity<String> login(LoginVo loginVo);
    ResultEntity<String> validateEmail(String email);
    ResultEntity<String> validateEmaiCode(ValidateEmailCodeDTO validateEmailCodeDTO);
    ResultEntity<String> register(Account account);
    void sendCodeForgotPassord(String email);
    void forgotPassword(ForgotPassowrdVo forgotPassowrdVo);
}
