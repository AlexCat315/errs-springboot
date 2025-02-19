package com.x.backend.pojo.admin.vo.request.user;

import lombok.Data;

@Data
public class UpdateUserRoleVO {
    private Integer userId;
    private String role;
}
