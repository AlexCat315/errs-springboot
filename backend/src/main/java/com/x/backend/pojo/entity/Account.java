package com.x.backend.pojo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements Serializable {
    private Integer aId;
    private String username;
    private String password;
    private String email;
    private String Role;
}
