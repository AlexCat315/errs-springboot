<script lang="ts" setup>
import LeftPanel from "./pages/LeftPanel.vue";
import { onBeforeMount, onBeforeUnmount, onMounted, ref } from "vue";
import { callConflateImg } from "./script/callConflateImg";
import RightPanel from "./pages/RightPanel.vue";

import { provide } from 'vue';
// 定义全局变量
const globalSelect = ref(0);

// 提供全局变量
provide('globalSelect', globalSelect);


// 定义一个颜色主题全局变量
const globalTheme = ref('light');
provide('globalTheme',globalTheme);
const selectTheme = ref(globalTheme)

const globalShowSetting = ref(true);
provide('globalShowSetting', globalShowSetting);


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

onBeforeMount(() => {
  callConflateImg()
});
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
      <LeftPanel id="left-panel" :distance-to-left="rightPanelOffset" />
    </div>
    <!-- 右侧内容 -->
    <div class="right-panel" :style="{'--background-color' : selectTheme === 'light' ? '#FFF' : 'black'}"  >
      <RightPanel  />
    </div>
  </div>
</template>

<style>
.page {
  position: relative;
  display: flex;
  height: 100vh;
}

@keyframes moveBackground {
  0% {
    background-position: left;
  }

  50% {
    background-position: right;
  }

  100% {
    background-position: left;
  }
}

.page::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('../src/assets/background/background.png');
  background-size: cover;
  background-position: left;
  -webkit-backdrop-filter: blur(10px);
  backdrop-filter: blur(10px);
  z-index: -1;
  animation: moveBackground 360s linear infinite;
}

.left-panel {
  position: relative;
  width: 260px;
  height: 100%;
  display: flex;
  flex-direction: column;
  backdrop-filter: blur(30px);
  -webkit-backdrop-filter: blur(30px);
  background-color: rgba(255, 255, 255, 0.3);
  z-index: 1;
}


.right-panel {
  width: 100%;
  height: 100vh;
  background-color: var(--background-color);
  border-radius: 5px;
  z-index: 1;
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
