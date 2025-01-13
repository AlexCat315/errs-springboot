package com.x.backend.pojo.user.entity

import com.x.backend.pojo.common.Account
import lombok.Data
import lombok.EqualsAndHashCode
import java.io.Serializable

@EqualsAndHashCode(callSuper = true)
@Data
class UserAccount : Account(), Serializable