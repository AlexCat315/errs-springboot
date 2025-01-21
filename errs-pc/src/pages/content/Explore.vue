<script setup lang="ts">
import { inject, onBeforeUnmount, onMounted, ref } from 'vue';
import WeatherCard from './explore/WeatherCard.vue';


const globalTheme = inject<string>("globalTheme");
const selectTheme = ref(globalTheme);

const showTitle = ref(true); // 控制标题显示状态

const handleScroll = () => {
  const scrollTop = window.scrollY || document.documentElement.scrollTop; // 获取滚动高度
  showTitle.value = scrollTop > 100; // 当滚动高度超过100px时显示标题
};

// 添加滚动事件监听
onMounted(() => {
  window.addEventListener("scroll", handleScroll);
});

// 移除滚动事件监听
onBeforeUnmount(() => {
  window.removeEventListener("scroll", handleScroll);
});

</script>


<template>
  <div class="explore_content" :style="{ backgroundColor: selectTheme === 'light' ? '#FFF' : '#262727' }">
    <!-- 顶部标题 -->
    <div class="title" :class="{ show: showTitle }">
      这是顶部的标题
    </div>
    <!-- 图片卡片 -->
    <div class="explore_img_card"></div>
    <div>
      <h1>1111</h1>
      <h1>1111</h1>
      <h1>1111</h1>
      <h1>1111</h1>
      <h1>1111</h1>
      <h1>1111</h1>
      <h1>1111</h1>
      <h1>1111</h1>
      <h1>1111</h1>
      <h1>1111</h1>   <h1>1111</h1>

      <h1>1111</h1>   <h1>1111</h1>   <h1>1111</h1>   <h1>1111</h1>


      <h1>1111</h1>   <h1>1111</h1>   <h1>1111</h1>   <h1>1111</h1>
    </div>
  </div>
</template>

<style scoped>
.explore_content {
  height: 100vh;
  overflow-y: auto; /* 启用滚动 */
}

.title {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  padding: 15px 20px;
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  color: white;
  background: linear-gradient(to right, #4caf50, #81c784);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1;
  opacity: 0;
  transform: translateY(-100%); /* 默认隐藏在视口上方 */
  transition: all 0.3s ease-in-out; /* 平滑过渡效果 */
}

.title.show {
  opacity: 1;
  transform: translateY(0); /* 滚动后显示 */
}

.explore_img_card {
  cursor: move;
  background-image: url("../../assets/img/landscape.jpg");
  background-size: cover;
  background-position: center;
  margin: 0 auto;
  border-radius: 8px;
  border: 3px dashed transparent;
  width: 90%;
  height: 330px;
  margin-top: 10px;
}
</style>