package com.x.backend.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class InsertInviteDTO  implements Serializable {
    // 邀请人ID
    private Integer aId;
    // 被邀请人ID
    private Integer invitedId;
}
