<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

interface User {
  id: number
  username: string
  email: string
  status: string
  lastLoginTime: string
  role: string
}

const users = ref<User[]>([])
const loading = ref(false)

const roles = ['admin', 'user', 'guest']

const getUsers = async () => {
  loading.value = true
  try {
    // Mock data - replace with actual API call
    users.value = [
      {
        id: 1,
        username: 'user1',
        email: 'user1@example.com',
        status: 'active',
        lastLoginTime: '2023-01-01 12:00:00',
        role: 'admin'
      },
      {
        id: 2, 
        username: 'user2',
        email: 'user2@example.com',
        status: 'banned',
        lastLoginTime: '2023-01-02 13:00:00',
        role: 'user'
      }
    ]
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
    const action = user.status === 'active' ? '封禁' : '解封'
    await ElMessageBox.confirm(`确定要${action}该用户吗?`, '提示', {
      type: 'warning'
    })
    // Call API to toggle ban status
    user.status = user.status === 'active' ? 'banned' : 'active'
    ElMessage.success(`${action}成功`)
  } catch {
    // User cancelled
  }
}

const changeRole = async (user: User, newRole: string) => {
  try {
    await ElMessageBox.confirm(`确定要将用户角色修改为${newRole}吗?`, '提示', {
      type: 'warning'
    })
    // Call API to change role
    user.role = newRole
    ElMessage.success('角色修改成功')
  } catch {
    // User cancelled
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
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="role" label="权限">
        <template #default="{ row }">
          <el-tag :type="row.role === 'admin' ? 'danger' : row.role === 'user' ? 'warning' : 'info'">
            {{ row.role }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="row.status === 'active' ? 'success' : 'danger'">
            {{ row.status === 'active' ? '正常' : '已封禁' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="lastLoginTime" label="最后登录时间" />
      <el-table-column label="操作" width="350">
        <template #default="{ row }">
          <el-button size="small" @click="resetPassword(row.id)">
            重置密码
          </el-button>
          <el-button 
            size="small"
            :type="row.status === 'active' ? 'danger' : 'primary'"
            @click="toggleBan(row)"
          >
            {{ row.status === 'active' ? '封禁' : '解封' }}
          </el-button>
          <el-select 
            v-model="row.role" 
            size="small" 
            style="width: 100px; margin-left: 10px"
            @change="(val) => changeRole(row, val)"
          >
            <el-option
              v-for="role in roles"
              :key="role"
              :label="role"
              :value="role"
            />
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