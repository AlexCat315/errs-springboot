<script setup>
import {onBeforeUnmount, provide, ref} from 'vue';
import {verifyToken} from "./net/token.ts";

// 定义全局变量
const globalSelect = ref("1");
const globalSearch = ref("");

// 提供全局变量
provide('globalSelect', globalSelect);
// 提供全局变量搜索
provide("globalSearch", globalSearch);
// 全局定时器
// 定时器用于验证token
const verifyTimer = setInterval(() => {
  let token = localStorage.getItem('token');
  if (!token) {
    token = sessionStorage.getItem('token');
    if (!token) {
      return;
    }
  }
  verifyToken();
}, 10000);  //10s

// 定时器用于刷新token
const refreshTimer = setInterval(() => {
  let token = localStorage.getItem('token');
  if (!token) {
    token = sessionStorage.getItem('token');
    if (!token) {
      return;
    }
  }
  verifyToken(true);
}, 60000 * 3); //3 minutes

// 组件销毁时清除定时器
onBeforeUnmount(() => {
  clearInterval(verifyTimer);
  clearInterval(refreshTimer);
});
</script>

<template>
  <router-view v-slot="{ Component }">
    <transition name="scale" mode="out-in">
      <component :is="Component"/>
    </transition>
  </router-view>
</template>

<style>
body,
:root {
  font-family: Inter, Avenir, Helvetica, Arial, sans-serif;
  font-size: 16px;
  line-height: 24px;
  font-weight: 400;
  color: #0f0f0f;
  font-synthesis: none;
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  -webkit-text-size-adjust: 100%;
}
.scale-enter-active, .scale-leave-active {
  transition: transform 0.5s, opacity 0.5s;
}

.scale-enter-from {
  transform: scale(0.8);
  opacity: 0;
}

.scale-leave-to {
  transform: scale(1.2);
  opacity: 0;
}

/* 自定义字体 */
@font-face {
  font-family: 'yousu-title-black';
  src: url('../src/assets/ttf/优设标题黑.ttf');
}

@font-face {
  font-family: 'LXGWMarkerGothic-Regular';
  src: url('../src/assets/ttf/LXGWMarkerGothic-Regular.ttf');
}
</style>