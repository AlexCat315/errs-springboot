<script setup lang="ts">
import {inject, onBeforeUnmount, onMounted, reactive, ref} from "vue";
import Explore from "./explore/Index.vue";

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

// 轮播图数据和状态
const images = reactive([
    "src/assets/img/title/title_01.png",
    "src/assets/img/title/title_04.png",
    "src/assets/img/title/title_05.webp",
    "src/assets/img/title/title_06.png",
]);
const currentIndex = ref(0);
const transitioning = ref(false); // 控制过渡状态

// 定时器
let autoSwitchTimer: NodeJS.Timeout | null = null;

// 切换到上一张图片
const prevImage = () => {
    if (transitioning.value) return;
    transitioning.value = true;
    startAutoSwitch();
    setTimeout(() => {
        currentIndex.value =
            (currentIndex.value - 1 + images.length) % images.length;
        transitioning.value = false;
    }, 500); // 动画时间需与 CSS 对应
};

// 切换到下一张图片
const nextImage = () => {
    if (transitioning.value) return;
    transitioning.value = true;
    startAutoSwitch();
    setTimeout(() => {
        currentIndex.value = (currentIndex.value + 1) % images.length;
        transitioning.value = false;
    }, 500); // 动画时间需与 CSS 对应
};

// 控制按钮显示状态
const showButtons = ref(false);

// 开始自动切换
const startAutoSwitch = () => {
    stopAutoSwitch(); // 确保不重复启动
    autoSwitchTimer = setInterval(() => {
        nextImage();
    }, 4000); // 每 4 秒切换一次
};

// 停止自动切换
const stopAutoSwitch = () => {
    if (autoSwitchTimer) {
        clearInterval(autoSwitchTimer);
        autoSwitchTimer = null;
    }
};

// 生命周期
onMounted(() => {
    startAutoSwitch(); // 启动自动切换
});

onBeforeUnmount(() => {
    stopAutoSwitch(); // 页面卸载时清除定时器
});
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
            探索
        </div>

        <!-- 轮播图 -->
        <div
            class="carousel"
            @mouseenter="showButtons = true"
            @mouseleave="showButtons = false"
        >
            <div class="carousel-img" :class="{ fade: transitioning }">
                <img :src="images[currentIndex]" alt="Carousel Image" />
            </div>
            <button v-if="showButtons" class="prev-button" @click="prevImage">
                <svg
                    t="1737523739102"
                    class="icon"
                    viewBox="0 0 1024 1024"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    p-id="10941"
                    width="22"
                    height="22"
                >
                    <path
                        d="M671.968176 911.99957c-12.287381 0-24.576482-4.67206-33.951566-14.047144L286.048434 545.984249c-18.751888-18.719204-18.751888-49.12028 0-67.872168L638.016611 126.111222c18.751888-18.751888 49.12028-18.751888 67.872168 0 18.751888 18.719204 18.751888 49.12028 0 67.872168l-318.016611 318.047574L705.888778 830.047574c18.751888 18.751888 18.751888 49.12028 0 67.872168C696.544658 907.32751 684.255557 911.99957 671.968176 911.99957z"
                        fill="#FFF"
                        p-id="10942"
                    ></path>
                </svg>
            </button>
            <button v-if="showButtons" class="next-button" @click="nextImage">
                <svg
                    t="1737523827842"
                    class="icon"
                    viewBox="0 0 1024 1024"
                    version="1.1"
                    xmlns="http://www.w3.org/2000/svg"
                    p-id="12168"
                    width="22"
                    height="22"
                >
                    <path
                        d="M761.055557 532.128047c0.512619-0.992555 1.343475-1.823411 1.792447-2.848649 8.800538-18.304636 5.919204-40.703346-9.664077-55.424808L399.935923 139.743798c-19.264507-18.208305-49.631179-17.344765-67.872168 1.888778-18.208305 19.264507-17.375729 49.631179 1.888778 67.872168l316.960409 299.839269L335.199677 813.631716c-19.071845 18.399247-19.648112 48.767639-1.247144 67.872168 9.407768 9.791372 21.984142 14.688778 34.560516 14.688778 12.000108 0 24.000215-4.479398 33.311652-13.439914l350.048434-337.375729c0.672598-0.672598 0.927187-1.599785 1.599785-2.303346 0.512619-0.479935 1.056202-0.832576 1.567101-1.343475C757.759656 538.879828 759.199462 535.391265 761.055557 532.128047z"
                        fill="#FFF"
                        p-id="12169"
                    ></path>
                </svg>
            </button>
        </div>
        <div>
            <Explore />
            <!-- 省略其他内容 -->
        </div>
    </div>
</template>

<style scoped lang="css">
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
    z-index: 1;
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

.carousel {
    position: relative;
    width: 90%;
    height: 330px;
    margin: 20px auto;
    overflow: hidden;
    border-radius: 8px;
    background-color: #191919;
}

.carousel-img {
    width: 100%;
    height: 100%;
    position: relative;
    transition: opacity 0.5s ease-in-out;
    /* 平滑过渡效果 */
    opacity: 1;
}

.carousel-img.fade {
    opacity: 0;
}

.carousel-img img {
    width: 100%;
    height: 100%;
}

.prev-button,
.next-button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background-color: rgba(0, 0, 0, 0.5);
    color: #292929;
    border: none;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    font-size: 16px;
    cursor: pointer;
    z-index: 2;
    opacity: 0;
    transition: opacity 0.3s ease-in-out;
}

.prev-button {
    left: 10px;
}

.next-button {
    right: 10px;
}

.carousel:hover .prev-button,
.carousel:hover .next-button {
    opacity: 1;
}
</style>
