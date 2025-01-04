<script lang="ts" setup>
import LeftPanel from "./pages/LeftPanel.vue";
import {onBeforeMount, onBeforeUnmount, onMounted, provide, ref} from "vue";
import {callConflateImg} from "./assets/script/callConflateImg";

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
    <div class="right-panel">
      <!-- 其他内容 -->
    </div>
  </div>
</template>

<style scoped>
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
  background-color: rgba(255,255,255,0.3);
}


.right-panel {
  width: 100%;
  height: 100vh;
  background-color: #fff;
  border-radius: 5px;
}
</style>
