package com.x.backend.pojo.admin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Invite implements Serializable {
    private Integer iId;
    // 邀请人ID
    private Integer aId;
    // 被邀请人ID
    private Integer invitedId;
    // 邀请时间
    private Date inviteTime;
    // 邀请状态是否同意
    private Integer status;
    // 处理时间
    private Date handleTime;
}
