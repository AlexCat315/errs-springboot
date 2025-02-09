<template>
    <div
        class="carousel-container"
        @mouseover="showButtons = true"
        @mouseleave="showButtons = false"
    >
        <div @click="goDetail()" :class="['carousel-card', mediaPosition]">
            <div class="media-content">
                <div
                    v-if="currentMedia.type === 'image'"
                    class="image-carousel"
                >
                    <img
                        :src="currentMedia.src"
                        :alt="'Image ' + (currentIndex + 1)"
                    />
                </div>

                <video
                    @click.stop
                    v-else-if="currentMedia.type === 'video'"
                    controls
                    :key="currentIndex"
                    autoplay
                    @play="onVideoPlay()"
                    @ended="onVideoEnded()"
                    @canplay="onVideoPlay()"
                    muted
                    ref="videoPlayer"
                >
                    <source :src="currentMedia.src" type="video/mp4" />
                    Your browser does not support the video tag.
                </video>
            </div>

            <div class="text-content">
                <h2
                    style="
                        font-family: Arial, Helvetica, sans-serif;
                        color: #fff;
                    "
                >
                    {{ currentText.title }}
                </h2>
                <p style="color: #fff">{{ currentText.description }}</p>
            </div>
        </div>

        <button v-show="showButtons" class="prev-button" @click="prevImage">
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
        <button v-show="showButtons" class="next-button" @click="nextImage">
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
</template>

<script lang="ts" setup>
import {computed, defineEmits, onBeforeUnmount, onMounted, ref} from "vue";

// 使用 emit 触发自定义事件
const emit = defineEmits();

const showButtons = ref(false); // 控制按钮显示状态

const props = defineProps({
    mediaList: {
        type: Array as () => { type: "video" | "image"; src: string }[],
        required: true,
    },
    textList: {
        type: Array as () => { title: string; description: string }[],
        required: true,
    },
});

const currentIndex = ref(0);
const videoPlayer = ref<HTMLVideoElement | null>(null);
const waitingForVideo = ref(false);

const currentMedia = computed(() => props.mediaList[currentIndex.value]);
const currentText = computed(() => props.textList[currentIndex.value]);
const mediaPosition = ref(Math.random() > 0.5 ? "left" : "right");

const goDetail = () => {
    emit("currentIndexChanged", currentIndex.value); // 发出当前索引的事件
};

const updatePositions = () => {
    mediaPosition.value = Math.random() > 0.5 ? "left" : "right";
};

const prevImage = () => {
    currentIndex.value =
        (currentIndex.value - 1 + props.mediaList.length) %
        props.mediaList.length;
    updatePositions();
    onVideoEnded();
};

const nextImage = () => {
    currentIndex.value = (currentIndex.value + 1) % props.mediaList.length;
    updatePositions();
    onVideoEnded();
};

// 视频播放时设置为正在等待
const onVideoPlay = () => {
    waitingForVideo.value = true;
};

// 视频播放结束时，自动切换
const onVideoEnded = () => {
    waitingForVideo.value = false;
    resetAutoSwitch();
};

// 重置自动切换计时器
const resetAutoSwitch = () => {
    clearInterval(autoSwitchInterval);
    autoSwitchInterval = setInterval(() => {
        if (!waitingForVideo.value) {
            nextImage();
        }
    }, 6000);
};

let autoSwitchInterval: ReturnType<typeof setInterval>;

onMounted(() => {
    autoSwitchInterval = setInterval(() => {
        if (!waitingForVideo.value) {
            nextImage();
        }
    }, 6000);
});

onBeforeUnmount(() => {
    clearInterval(autoSwitchInterval);
});
</script>

<style scoped>
.carousel-container {
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    width: 90%;
    max-width: 1200px;
    margin: 0 auto;
    overflow: hidden;
    z-index: 99;
    cursor: pointer;
}

.carousel-card {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    width: 100%;
    max-width: 1200px;
    /* 设置背景色，例如可以使用纯色或其他你想要的颜色 */
    background-color: #232d2d; /* 注意颜色码需要有效，这里用6位16进制 */
    background-image: radial-gradient(
            circle at 10% 20%,
            rgba(255, 255, 255, 0.1) 15%,
            transparent 16%
        ),
        radial-gradient(
            circle at 70% 40%,
            rgba(255, 255, 255, 0.1) 15%,
            transparent 16%
        ),
        radial-gradient(
            circle at 30% 80%,
            rgba(255, 255, 255, 0.1) 15%,
            transparent 16%
        ),
        radial-gradient(
            circle at 80% 90%,
            rgba(255, 255, 255, 0.1) 15%,
            transparent 16%
        );
    background-size: 5px 5px;
    border-radius: 16px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    transition: transform 0.5s ease;
    flex-direction: row;
    height: 360px;
}

.carousel-card.left {
    flex-direction: row-reverse;
}

.text-content {
    flex: 1;
    padding: 20px;
    transition: all 0.5s ease;
}

.media-content {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: all 0.5s ease;
}

.image-carousel img {
    max-width: 550px;
    height: auto;
    border-radius: 8px;
}

video {
    max-width: 550px;
    height: auto;
    border-radius: 8px;
}

.prev-button,
.next-button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background-color: rgba(0, 0, 0, 0.5);
    color: white;
    border: none;
    padding: 10px;
    cursor: pointer;
    z-index: 10;
    opacity: 0; /* 默认隐藏 */
    transition: opacity 0.3s ease;
    border-radius: 7px;
}

.prev-button {
    left: 10px;
}

.next-button {
    right: 10px;
}

/* 鼠标移入时显示按钮 */
.carousel-container:hover .prev-button,
.carousel-container:hover .next-button {
    opacity: 1;
}
</style>
