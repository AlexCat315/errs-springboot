package com.x.backend.pojo.common

import lombok.Data
import lombok.Getter
import lombok.Setter
import java.util.*

@Data
@Getter
@Setter
open class Account {
    var aId: Int? = null
    var username: String? = null
    var password: String? = null
    var email: String? = null
    var role: String? = null
    var vip: String? = null
    var createdAt: Date? = null // 创建时间
    var inviteCode: String? = null // 邀请码

    // 是否封禁
    var isBanned: Boolean? = null // true:封禁 false:未封禁
}
