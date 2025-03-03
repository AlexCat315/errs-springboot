package com.x.backend.service.admin;

import com.x.backend.pojo.admin.entity.Invite;
import com.x.backend.pojo.admin.vo.request.user.InviteVO;
import com.x.backend.pojo.admin.vo.request.user.SearchAccountVO;
import com.x.backend.pojo.admin.vo.request.user.UpdateUserRoleVO;
import com.x.backend.pojo.common.Account;
import com.x.backend.pojo.common.PageSize;

import java.util.List;

public interface UserService {
    List<Account> getAll(PageSize pageSize);

    void updateUserRole(UpdateUserRoleVO updateUserRoleVO);

    void resetPassword(Account account);

    void updateBanned(Account account);

    Account getAccountById(Integer id);

    List<Account> searchInfo(SearchAccountVO searchAccountVO);

    void updateImgUrl(Account account);

    Invite getInviteByUserId(Integer id,Integer inviteId);

    void updateInviteStatus(Invite invite);

    List<InviteVO> getInviteListByUserId(Integer inviteId);

    List<InviteVO> getInviteHistoryListByUserId(Integer inviteId);
}
