<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { get_user_info_all } from "../../../net/user/get"
import { update_user_role } from "../../../net/user/update"
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
    try {
        await ElMessageBox.confirm('确定要重置该用户的密码吗?', '提示', {
            type: 'warning'
        })
        // Call API to reset password
        ElMessage.success('密码重置成功')
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
        // Call API to toggle ban status
        user.isBanned = !user.isBanned
        ElMessage.success(`${action}成功`)
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
</script>

<template>
    <div class="user-management">
        <h2>用户管理</h2>

        <el-table v-loading="loading" :data="users" border>
            <el-table-column prop="aid" label="ID" width="80" />
            <el-table-column prop="username" label="用户名" />
            <el-table-column prop="email" label="邮箱" />
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
                    <el-button size="small" @click="resetPassword(row.aId)">
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
</style>
