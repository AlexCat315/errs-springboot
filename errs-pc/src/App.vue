<script lang="ts" setup>
import LeftPanel from "./pages/LeftPanel.vue";
import { onBeforeMount, onBeforeUnmount, onMounted, provide, ref } from "vue";
import RightPanel from "./pages/RightPanel.vue";
import { get_system_theme } from "./util/Theme";
// 定义全局变量
const globalSelect = ref(1);

// 提供全局变量
provide("globalSelect", globalSelect);
const globalSearch = ref("");
provide("globalSearch", globalSearch);
// 定义一个颜色主题全局变量
const globalTheme = ref("");
const theme = ref("");

const baseDevUrl = "http://localhost:12345";
const baseProdUrl = "https://www.alexcat.it.com";
const globalBaseUrl = ref(baseDevUrl);
provide("globalBaseUrl", globalBaseUrl);

// 定时器
setInterval(() => {
    const theme = ref(localStorage.getItem("theme"));
    if (theme.value === "system") {
        get_system_theme().then((isDark) => {
            if (isDark) {
                globalTheme.value = "dark";
            } else {
                globalTheme.value = "light";
            }
        });
    }
}, 1000);
provide("globalTheme", globalTheme);
const selectTheme = ref(globalTheme);

const globalShowSetting = ref(true);
provide("globalShowSetting", globalShowSetting);

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
const themeRefech = ref(true);

onMounted(() => {
    theme.value = localStorage.getItem("theme");
    console.log("theme", theme.value);
    if (theme !== null && theme.value !== undefined && theme.value !== null && theme.value !== '') {
        if (theme.value === "system") {
            get_system_theme().then((isDark) => {
                if (isDark) {
                    globalTheme.value = "dark";
                } else {
                    globalTheme.value = "light";
                }
            });
        } else {
            globalTheme.value = theme.value;
        }
    } else {

        globalTheme.value = "light";
        localStorage.setItem("theme", "light");
        themeRefech.value = false;
        setTimeout(() => {
            themeRefech.value = true;
        }, 10);
    }
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
    <div v-if="themeRefech" class="page" style="display: flex">
        <!-- 左侧菜单栏 -->
        <div class="left-panel">
            <LeftPanel id="left-panel" :distance-to-left="rightPanelOffset" />
        </div>
        <!-- 右侧内容 -->
        <div :style="{
            '--background-color':
                selectTheme === 'light' ? '#FFF' : 'black',
        }" class="right-panel">
            <RightPanel />
        </div>
    </div>
</template>

<style>
.page {
    position: relative;
    display: flex;
    height: 100%;
}

* {
    user-select: none;
    /* 禁止所有元素文本选择 */
    -webkit-user-select: none;
    /* Safari/Chrome */
    -moz-user-select: none;
    /* Firefox */
    -ms-user-select: none;
    /* IE/Edge */
}

/* 允许输入框和文本域选择文本 */
input,
textarea {
    user-select: text !important;
    -webkit-user-select: text !important;
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
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-image: url("../src/assets/background/background.png");
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
    z-index: 9999 !important;
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
    font-family: "yousu-title-black";
    src: url("../src/assets/ttf/优设标题黑.ttf");
}

@font-face {
    font-family: "LXGWMarkerGothic-Regular";
    src: url("../src/assets/ttf/LXGWMarkerGothic-Regular.ttf");
}

@font-face {
    font-family: "wenquanyimihei";
    src: url("../src/assets/ttf/文泉驿微米黑.ttc");
}

@font-face {
    font-family: "rate-number-font";
    src: url("../src/assets/ttf/TapTapRatings-Regular.woff2");
}
</style>
