<script setup>
import {logoutJS} from "../net/account/Logout.js"
import router from "../router/router.js";
import {ElMessage} from "element-plus";

function logout() {
  logoutJS(() => {
    // 删除token
    localStorage.removeItem('token');
    sessionStorage.removeItem('token');
    router.push({name: 'login'});
    ElMessage.success('退出成功');
  }, () => {
    ElMessage.error('退出失败');
  })
}

</script>

<template>
  <el-button @click="logout" type="primary">退出登录</el-button>
</template>

<style scoped>

</style>