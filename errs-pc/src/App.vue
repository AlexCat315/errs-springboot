<script setup>
import { ref } from "vue";
import { invoke } from "@tauri-apps/api/core";
import XButton from "./components/XButton.vue";

const greetMsg = ref("");

async function greet() {
  // Learn more about Tauri commands at https://tauri.app/develop/calling-rust/
  greetMsg.value = await invoke("greet", { name: "123112312" });
}
</script>

<template>
  <div class="page" style="display: flex;">
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
  display: flex;
  flex-direction: column;
}

.right-panel {
  width: 100%;
  height: 100vh;
  background-color: #fff;
  /* 四周边框圆角 */
  border-radius: 10px;
}
</style>
