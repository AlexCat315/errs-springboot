package com.x.backend.pojo.admin.vo.request.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InviteVO implements Serializable {
    private Integer iId;
    // 被邀请人ID
    private Integer aId;
    // 邀请人ID     
    private Integer invitedId;
    // 邀请时间
    private Date inviteTime;
    // 邀请状态是否同意
    private Integer status;
    // 处理时间
    private Date handleTime;
    // 处理结果
    private String result;
    // 用户名
    private String username;
    // 邀请码
    private String inviteCode;
    // 邮箱
    private String email;
}
