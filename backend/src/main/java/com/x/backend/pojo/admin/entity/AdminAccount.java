package com.x.backend.pojo.admin.entity;

import com.x.backend.pojo.common.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminAccount extends Account implements Serializable {

}
