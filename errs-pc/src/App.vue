<script setup>
import {ref} from "vue";
import {invoke} from "@tauri-apps/api/core";

const greetMsg = ref("");
const name = ref("");

async function greet() {
  // Learn more about Tauri commands at https://tauri.app/develop/calling-rust/
  greetMsg.value = await invoke("greet", {name: name.value});
}
</script>

<template>
<div style="display: flex;">
  <!-- 左侧菜单栏 -->
  <div class="left-panel">

  </div>
  <!-- 右侧内容 -->
  <div class="right-panel">

  </div>
</div>
</template>

<style scoped>
body,
#app {
  margin: 0;
  padding: 0;
}

:host {
  margin: 0;
  padding: 0;
}
.left-panel {
  width: 200px;
  height: 100%;
  background-color: #f5f5f5;
}
</style>

