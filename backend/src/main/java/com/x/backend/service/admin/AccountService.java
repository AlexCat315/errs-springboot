package com.x.backend.service.admin;

import com.x.backend.pojo.admin.dto.InsertInviteDTO;
import com.x.backend.pojo.admin.entity.AdminAccount;

public interface AccountService {
    
    AdminAccount login(String username);

    void findByEmail(String email);

    Integer register(AdminAccount adminAccount);

    void isEmailExists(String email);

    void updatePassword(AdminAccount adminAccount);

    Integer findByInviteCode(String inviteCode);

    void insertInvite(InsertInviteDTO insertInviteDTO);

    AdminAccount findAccountByNameOrEmail(String username);

    void isUsernameExists(String username);


    AdminAccount findById(Integer id);

    void updateLastLoginTime(Integer aId);

    void updateBanned(int userId, boolean b);

}
