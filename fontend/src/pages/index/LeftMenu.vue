<script setup>
import { ref } from 'vue'
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting, ArrowLeft, ArrowRight,
} from '@element-plus/icons-vue'


const handleOpen = (key, keyPath) => {
  console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}
const simpleMenu = ref(false)
const menuText = ref('折叠菜单')
function isShowSimpleMenu() {
   simpleMenu.value = !simpleMenu.value;
   menuText.value = simpleMenu.value ? '展开菜单' : '折叠菜单';
}
</script>

<template>
  <div style="display: flex">
    <p style="flex:1;font-family: system-ui;font-size: 11px">文娱推荐与评分系统</p>
    <el-radio-group v-model="simpleMenu" style="flex: 3">
      <el-button v-if="!simpleMenu"  size="small" circle @click="isShowSimpleMenu" :value="simpleMenu" :icon="ArrowLeft"></el-button>
      <el-button v-else size="small"  @click="isShowSimpleMenu" :value="simpleMenu" :icon="ArrowRight"></el-button>
    </el-radio-group>
  </div>

  <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      :collapse="simpleMenu"
      @open="handleOpen"
      @close="handleClose"
  >
    <el-sub-menu index="1">
      <template #title>
        <el-icon><location /></el-icon>
        <span>Navigator One</span>
      </template>
      <el-menu-item-group>
        <template #title><span>Group One</span></template>
        <el-menu-item index="1-1">item one</el-menu-item>
        <el-menu-item index="1-2">item two</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="Group Two">
        <el-menu-item index="1-3">item three</el-menu-item>
      </el-menu-item-group>
      <el-sub-menu index="1-4">
        <template #title><span>item four</span></template>
        <el-menu-item index="1-4-1">item one</el-menu-item>
      </el-sub-menu>
    </el-sub-menu>
    <el-menu-item index="2">
      <el-icon><icon-menu /></el-icon>
      <template #title>Navigator Two</template>
    </el-menu-item>
    <el-menu-item index="3" disabled>
      <el-icon><document /></el-icon>
      <template #title>Navigator Three</template>
    </el-menu-item>
    <el-menu-item index="4">
      <el-icon><setting /></el-icon>
      <template #title>Navigator Four</template>
    </el-menu-item>
  </el-menu>
</template>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>