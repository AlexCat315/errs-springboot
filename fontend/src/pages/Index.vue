<script setup>
import {logoutJS} from "../net/account/logout.js"
import router from "../router/router.js";
import {ElMessage} from "element-plus";
import {inject, ref, watch} from "vue";
import {Expand, Fold, House, com.x.backend.pojo.user.User} from "@element-plus/icons-vue";

function logout() {
  logoutJS(() => {
    // 删除token
    localStorage.removeItem('token');
    sessionStorage.removeItem('token');
    router.push({ name: 'login' });
    ElMessage.success('退出成功');
  }, () => {
    ElMessage.error('退出失败');
  })
}

const collapseBtnClass = ref("el-icon-s-fold");
const isCollapse = ref(false);
const sideWidth = ref(200);
const logoTextShow = ref(true);

const collapse = () => {
  isCollapse.value = !isCollapse.value;
  if (isCollapse.value) {
    sideWidth.value = 64;
    collapseBtnClass.value = "el-icon-s-unfold";
    logoTextShow.value = false;
  } else {
    sideWidth.value = 200;
    collapseBtnClass.value = "el-icon-s-fold";
    logoTextShow.value = true;
  }
};


const activeTextColor = ref("#FFd04b");

// 接收全局变量
const globalSelect = inject("globalSelect");
// 检查 globalSelect 是否存在并设置默认值
if (!globalSelect) {
  throw new Error("globalSelect is not provided");
}

const selectIndex = ref(globalSelect);

const handleSelect = (index) => {
  selectIndex.value = index;
};
// 监听全局变量变化
watch(globalSelect, (newValue, oldValue) => {
  if (newValue === oldValue) return;
  selectIndex.value = newValue;
});
const toMyInfo = () => {
  selectIndex.value = "5";
};

</script>

<template>
  <div style="
      font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI',
        Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue',
        sans-serif;
    ">
    <el-container style="height: 100%; min-height: 100vh">
      <el-aside :width="sideWidth + 'px'" style="
          background-color: rgb(238, 241, 246);
          min-height: 100%;
          box-shadow: 2px 0 6px #8459a5;
        ">
        <el-menu style="height: 100%; overflow-x: hidden" background-color="#7288fa" text-color="#FFFFFF"
          :active-text-color="activeTextColor" :collapse-transition="false" :collapse="isCollapse"
          @select="handleSelect" :default-active="selectIndex">
          <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/img/logo.png" alt="logo" style="
                width: 30px;
                position: relative;
                top: 5px;
                margin-right: 5px;
              " />
            <b class="logo-text" v-show="logoTextShow">文娱推荐与评分系统</b>
          </div>
          <el-menu-item @click="collapse" v-if="isCollapse">
            <el-icon>
              <Expand />
            </el-icon>
            <template #title>展开菜单</template>
          </el-menu-item>
          <el-menu-item index="1">
            <el-icon>
              <House />
            </el-icon>
            <template #title>主菜单</template>
          </el-menu-item>
          <el-menu-item index="2">
            <el-icon>
              <com.x.backend.pojo.user.User />
            </el-icon>

            <template #title>用户管理</template>
          </el-menu-item>

        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="
            font-size: 12px;
            border-bottom: 1px solid #ccc;
            line-height: 60px;
            display: flex;
          ">
          <div style="flex: 1; font-size: 20px">
            <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
            <el-button v-if="!isCollapse" type="info" plain round @click="collapse" :icon="Fold">折叠菜单
            </el-button>
          </div>
          <el-dropdown style="width: 70px; cursor: pointer">
            <el-button style="margin-top: 10px" :icon="com.x.backend.pojo.user.User" circle />

            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="toMyInfo()">个人信息</el-dropdown-item>
                <el-dropdown-item @click="toMyInfo()">修改密码</el-dropdown-item>
                <el-dropdown-item @click="logout()">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </el-header>

        <el-main>

        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
.logo-text {
  color: #fff;
  /* 字体颜色 */
  font-size: 16px;
  /* 字体大小 */
  font-weight: bold;
  /* 字体加粗 */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.6);
  /* 加上阴影效果 */
  margin-left: 10px;
  /* 左侧边距 */
  font-family: 'yousu-title-black',serif;
  /* 字体样式，可以替换为其他字体 */
}
</style>