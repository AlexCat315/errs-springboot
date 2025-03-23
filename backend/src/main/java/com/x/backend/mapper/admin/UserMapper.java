package com.x.backend.mapper.admin;

import com.x.backend.pojo.admin.entity.Invite;
import com.x.backend.pojo.admin.vo.request.user.InviteVO;
import com.x.backend.pojo.admin.vo.request.user.SearchAccountVO;
import com.x.backend.pojo.admin.vo.request.user.UpdateUserRoleVO;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.common.PageSize;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("adminUserMapper")
public interface UserMapper {
    List<Account> getAll(PageSize pageSize);

    Integer updateUserRole(UpdateUserRoleVO updateUserRoleVO);

    Integer resetPassword(Account account);

    Integer updateBanned(Account account);

    Account getAccountById(Integer id);

    List<Account> searchInfo(SearchAccountVO searchAccountVO);

    void updateImgUrl(Account account);

    Invite getInviteByUserId(@Param("id") Integer id, @Param("inviteId") Integer inviteId);

    Integer updateInviteStatus(Invite invite);

    List<InviteVO> getInviteListByUserId(@Param("inviteId") Integer inviteId);

    List<InviteVO> getInviteHistoryListByUserId(Integer inviteId);

    String getInviteById(@Param("id") Integer inviteId);

    int updateInviteCode(@Param("id") Integer inviteId,@Param("inviteCode") String inviteCode);
}
