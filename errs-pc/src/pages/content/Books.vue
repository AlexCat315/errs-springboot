<script setup lang="ts">
import { inject, ref, provide } from "vue";
import Index from "./books/Index.vue";

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

const globalShowBookList = ref(false);

// 提供全局变量
provide("globalShowBookList", globalShowBookList);
const globalShowBookListSelect = ref(1);

// 提供全局变量
provide("globalShowBookListSelect", globalShowBookListSelect);

const currentIndex = ref(null);

provide("currentIndex", currentIndex);

</script>

<template>
    <div
        class="explore_content"
        @scroll="handleScroll"
        :style="{
            backgroundColor: selectTheme === 'light' ? '#FFF' : '#202327',
        }"
    >
        <!-- 顶部标题 -->
        <div
            class="title"
            :style="{
                backgroundColor: selectTheme === 'light' ? '#FFF' : '#171717',
                color: selectTheme === 'light' ? '#525252' : '#e1e4e8',
            }"
            :class="{ show: showTitle }"
        >
            图书
        </div>

        <div>
            <Index />
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
    z-index: 10;
    opacity: 0;
    transform: translateY(-100%);
    transition: all 0.3s ease-in-out;
    border-radius: 8px;
    font-family: Verdana, Geneva, Tahoma, sans-serif;
    font-size: 15px;
    z-index: 11;
}

.title.show {
    opacity: 1;
    transform: translateY(0) !important;
}
</style>
