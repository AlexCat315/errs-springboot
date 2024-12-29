package com.x.backend.pojo.admin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FindInviteCodeDTO  implements Serializable {
    private Integer aId;
    private String role;
}
