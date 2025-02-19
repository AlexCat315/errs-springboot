package com.x.backend.mapper.admin;

import com.x.backend.pojo.admin.dto.FindInviteCodeDTO;
import com.x.backend.pojo.admin.dto.ForgotPasswordDTO;
import com.x.backend.pojo.admin.entity.AdminAccount;
import com.x.backend.pojo.admin.entity.Invite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component(value = "adminAccountMapper")
@Mapper
public interface AccountMapper {

    AdminAccount login(String username);

    @Select("select a_id from account where email = #{email}")
    Integer findByEmail(String email);

    Integer insert(AdminAccount adminAccount);

    @Update("update account set password = #{newPassword} where email = #{email}")
    Integer updatePassword(ForgotPasswordDTO forgotPasswordDTO);

    FindInviteCodeDTO findByInviteCode(String inviteCode);

    Integer insertInvite(Invite invite);

    @Select("select a_id,username,email from account where username = #{username} or email = #{username}")
    AdminAccount findAccountByNameOrEmail(String username);

    @Select("select a_id from account where username = #{username}")
    AdminAccount findAccountByName(String username);

    AdminAccount findById(Integer id);

    Integer updateLastLoginTime(Integer aId);
}
