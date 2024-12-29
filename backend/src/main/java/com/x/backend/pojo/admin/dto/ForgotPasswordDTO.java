package com.x.backend.pojo.admin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ForgotPasswordDTO implements Serializable {
    private String email;
    private String newPassword;
}
