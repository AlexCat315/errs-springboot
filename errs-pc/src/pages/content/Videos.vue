<script setup lang="ts">
import { inject, ref } from 'vue';

import VideoIndex from './videos/VideoIndex.vue';

const globalTheme = inject<string>("globalTheme");
const selectTheme = ref(globalTheme);
const showTitle = ref(false); // 控制标题显示状态

// 定义一个 ref 来存储滚动的 Y 轴距离
const scrollY = ref(0);

// 监听滚动事件的处理函数
const handleScroll = (event: Event) => {
    const target = event.target as HTMLElement;
    scrollY.value = target.scrollTop;
    if (scrollY.value > 100) {
        showTitle.value = true;
    } else {
        showTitle.value = false;
    }
};


</script>

<template>
    <div class="explore_content" @scroll="handleScroll"
        :style="{ backgroundColor: selectTheme === 'light' ? '#FFF' : '#202327' }">
        <!-- 顶部标题 -->
        <div class="title"
            :style="{ backgroundColor: selectTheme === 'light' ? '#FFF' : '#171717', color: selectTheme === 'light' ? '#525252' : '#e1e4e8' }"
            :class="{ show: showTitle }">
            电影
        </div>

        <div>
            <VideoIndex  />
        </div>
    </div>
</template>


<style scoped>
.explore_content {
    height: 100vh;
    overflow-y: auto;
    scrollbar-width: none;
    /* Firefox */
    width: calc(100%);
}

.container::-webkit-scrollbar {
    display: none;
    /* Chrome, Safari 和 Edge */
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
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    z-index: 99999 !important;
    opacity: 0;
    transform: translateY(-100%);
    transition: all 0.3s ease-in-out;
    border-radius: 8px;
    font-family: Verdana, Geneva, Tahoma, sans-serif;
    font-size: 15px;
}

.title.show {
    opacity: 1;
    transform: translateY(0);
}
</style>
