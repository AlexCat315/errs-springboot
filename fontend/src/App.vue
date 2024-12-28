<script setup>
import { provide, ref } from 'vue';

// 定义全局变量
const globalSelect = ref("1");

// 提供全局变量
provide('globalSelect', globalSelect);
</script>

<template>
  <router-view v-slot="{ Component }">
    <transition name="scale" mode="out-in">
      <component :is="Component" />
    </transition>
  </router-view>
</template>

<style>
body,
#app {
  margin: 0;
  padding: 0;
}

:host {
  margin: 0;
  padding: 0;
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