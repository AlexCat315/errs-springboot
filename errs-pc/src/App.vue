<script lang="ts" setup>

import LeftPanel from "./pages/LeftPanel.vue";
import {onBeforeUnmount, onMounted, ref} from "vue";

const rightPanelOffset = ref(0); // 存储右侧内容到左边的距离

// 更新右侧内容的偏移量
const updateRightPanelOffset = () => {
  const rightPanel = document.querySelector(".right-panel") as HTMLElement; // 断言为 HTMLElement
  if (rightPanel) {
    rightPanelOffset.value = rightPanel.offsetLeft;
  }
};


const observeLayoutChanges = () => {
  const observer = new ResizeObserver(() => {
    updateRightPanelOffset();
  });

  const page = document.querySelector(".page");
  if (page) {
    observer.observe(page);
  }

  return observer; // 返回 observer 以便销毁
};

let layoutObserver: ResizeObserver | null = null;

onMounted(() => {
  updateRightPanelOffset(); // 初始化距离
  layoutObserver = observeLayoutChanges(); // 开启布局变化监听
});

onBeforeUnmount(() => {
  if (layoutObserver) {
    layoutObserver.disconnect(); // 销毁观察器
  }
});
</script>

<template>
  <div class="page" style="display: flex;">
    <!-- 左侧菜单栏 -->
    <div class="left-panel">
      <LeftPanel id="left-panel" :distance-to-left="rightPanelOffset"  />
    </div>
    <!-- 右侧内容 -->
    <div class="right-panel">
      <!-- 其他内容 -->
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
  width: 260px;
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
