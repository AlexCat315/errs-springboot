<script setup>
import {logoutJS} from "../net/account/Logout.js"
import router from "../router/router.js";
import {ElMessage} from "element-plus";
import LeftMenu from "./index/LeftMenu.vue";

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
  <div style="display: flex">
    <div class="left">
    <LeftMenu />
    </div>
    <div class="right">
      <el-button @click="logout" type="primary">退出登录</el-button>
    </div>
  </div>

</template>

<style scoped>
.left {
  height: 100vh;
  background-color: #2362be;
}
.right {
  flex: 2;
  height: 100vh;
  background-color: #5e0707;
}
</style>