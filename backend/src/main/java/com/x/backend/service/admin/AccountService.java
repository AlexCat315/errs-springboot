package com.x.backend.service.admin;

import com.x.backend.pojo.admin.dto.InsertInviteDTO;
import com.x.backend.pojo.admin.entity.Account;

public interface AccountService {
    Account login(String username);

    void setToken(String token,Account account);

    void findByEmail(String email);

    Integer register(Account account);

    void isEmailExists(String email);

    void updatePassword(Account account);

    Integer findByInviteCode(String inviteCode);

    void insertInvite(InsertInviteDTO insertInviteDTO);

    Account findAccountByNameOrEmail(String username);

    void isUsernameExists(String username);


    Account findById(Integer id);
}
