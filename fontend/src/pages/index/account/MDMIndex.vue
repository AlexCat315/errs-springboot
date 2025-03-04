<script setup lang="ts">
import { ref, onMounted, watch, inject, Ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { get_user_info_all, get_user_info_search } from "../../../net/user/get"
import { update_user_role, update_user_password, update_user_unbanned, update_user_banned } from "../../../net/user/update"
import { h } from 'vue'
interface User {
    aid: number
    username: string
    email: string
    isBanned: boolean
    lastLoginTime: string
    role: string,
    status: string
}
const globalSearch = inject("globalSearch") as Ref<string>;
if (!globalSearch) {
    throw new Error("globalSearch is not provided");
}

const users = ref<User[]>([])
const loading = ref(false)

const roles = ['admin', 'user']


const getUsers = async () => {
    loading.value = true
    try {
        get_user_info_all(
            1, 10,
            (data: any) => {
                users.value = data
            }
        )
    } catch (error) {
        ElMessage.error('获取用户列表失败')
    }
    loading.value = false
}

const resetPassword = async (userId: number) => {
    console.log(userId)
    try {
        // 修改确认框样式
        await ElMessageBox.confirm('确定要重置该用户的密码吗?默认123456', '提示', {
            type: 'warning',
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            customClass: 'custom-message-box', // 自定义类名
            center: true, // 内容居中
            roundButton: true, // 圆角按钮
            buttonSize: 'default', // 按钮大小
            confirmButtonClass: 'confirm-button', // 确认按钮自定义类名
            cancelButtonClass: 'cancel-button', // 取消按钮自定义类名
            distinguishCancelAndClose: true // 区分取消与关闭
        })

        await update_user_password(userId, (data: any) => {
            // 修改成功提示样式
            ElMessage({
                message: '密码重置成功',
                type: 'success',
                duration: 2000, // 显示时间
                showClose: true, // 显示关闭按钮
                center: true, // 文字居中
                customClass: 'custom-message' // 自定义类名
            })
        })
    } catch {
        // User cancelled
    }
}

const toggleBan = async (user: User) => {
    try {
        const action = user.isBanned ? '解封' : '封禁'
        await ElMessageBox.confirm(`确定要${action}该用户吗?`, '提示', {
            type: 'warning'
        })

        if (action === '封禁') {
            await update_user_banned(user.aid, (data) => {
                user.isBanned = true
                ElMessage.success(`${action}成功`)
            })
        } else {
            await update_user_unbanned(user.aid, (data) => {
                user.isBanned = false
                ElMessage.success(`${action}成功`)
            })
        }

    } catch {
        // User cancelled
    }
}

const changeRole = async (user: User, newRole: string) => {
    const oldRole = user.role // 此时能正确获取原始角色
    try {
        await ElMessageBox.confirm(
            h('div', null, [
                h('p', { style: 'color: red' }, '修改角色?'),
                h('p', { style: 'color: #666' }, `将 ${user.username} 的角色从 ${oldRole} 改为 ${newRole}`)
            ]),
            {
                title: '提示',
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }
        )

        await update_user_role(user.aid, newRole, (data: any) => {
            user.role = newRole // API 成功后再更新角色
            ElMessage.success('角色修改成功')
        })
    } catch (error) {
        // 失败时不需要回滚，因为 user.role 未被提前修改
        ElMessage.error('角色修改失败')
    }
}

onMounted(() => {
    getUsers()
})

// 监听search变化
watch(globalSearch, (newValue) => {
    if (newValue === '' || newValue === undefined || newValue === null) {
        getUsers()
    } else {
        get_user_info_search(1, 10, newValue, (data: any) => {
            users.value = data
        })
    }
})
</script>

<template>
    <div class="user-management">
        <h2>用户管理</h2>

        <el-table v-loading="loading" :data="users" border>
            <el-table-column prop="aid" label="ID" width="80" />
            <el-table-column prop="username" label="用户名" width="150" />
            <el-table-column prop="email" label="邮箱" width="200" />
            <el-table-column prop="role" label="权限">
                <template #default="{ row }">
                    <el-tag :type="row.role === 'admin' ? 'danger' : row.role === 'user' ? 'warning' : 'info'">
                        {{ row.role }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="isBanned" label="状态">
                <template #default="{ row }">
                    <el-tag :type="!row.isBanned ? 'success' : 'danger'">
                        {{ !row.isBanned ? '正常' : '已封禁' }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="lastLoginTime" width="200" label="最后登录时间" />
            <el-table-column label="操作" width="350">
                <template #default="{ row }">
                    <el-button size="small" @click="resetPassword(row.aid)">
                        重置密码
                    </el-button>
                    <el-button size="small" :type="!row.isBanned ? 'danger' : 'primary'" @click="toggleBan(row)">
                        {{ !row.isBanned ? '封禁' : '解封' }}
                    </el-button>
                    <el-select :value="row.role" placeholder="修改权限" size="small" style="width: 100px; margin-left: 10px"
                        @change="(val: string) => changeRole(row, val)">
                        <el-option v-for="role in roles" :key="role" :label="role" :value="role" />
                    </el-select>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<style scoped>
.user-management {
    padding: 20px;
}

h2 {
    margin-bottom: 20px;
}

.custom-message-box {
    padding: 20px;
    border-radius: 8px;
}

.custom-message-box .el-message-box__header {
    padding: 15px 20px;
}

.custom-message-box .el-message-box__content {
    padding: 20px;
    font-size: 16px;
}

/* 确认按钮样式 */
.confirm-button {
    background-color: #409EFF;
    color: white;
}

/* 取消按钮样式 */
.cancel-button {
    border: 1px solid #DCDFE6;
    color: #606266;
}

/* 成功提示消息样式 */
.custom-message {
    min-width: 300px;
    padding: 12px 20px;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>
