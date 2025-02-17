<style scoped>
.card {
    --rounded: 20px;
    --size: 360px;
    width: var(--size);
    height: calc(var(--size) - 10px);
    border-radius: var(--rounded);
    background: #fff;
    position: relative;
    overflow: hidden;
    cursor: pointer;
}

.card::before {
    --size: 100%;
    --blur: 20px;
    content: "";
    position: absolute;
    width: var(--size);
    height: var(--size);
    filter: blur(var(--blur));
    background-color: #faff99;
    background-image: radial-gradient(at 33% 82%, hsla(254, 71%, 69%, 1) 0px, transparent 50%),
        radial-gradient(at 28% 4%, hsla(289, 96%, 63%, 1) 0px, transparent 50%),
        radial-gradient(at 69% 49%, hsla(309, 91%, 71%, 1) 0px, transparent 50%),
        radial-gradient(at 94% 14%, hsla(232, 66%, 62%, 1) 0px, transparent 50%),
        radial-gradient(at 19% 93%, hsla(51, 98%, 74%, 1) 0px, transparent 50%),
        radial-gradient(at 15% 80%, hsla(194, 87%, 63%, 1) 0px, transparent 50%),
        radial-gradient(at 56% 52%, hsla(109, 71%, 61%, 1) 0px, transparent 50%);
}

.card::after {
    --size: 100%;
    content: "";
    position: absolute;
    width: var(--size);
    height: var(--size);
    background: rgba(255, 255, 255, 0.5);
}

.card__view {
    position: absolute;
    height: 55%;
    left: 0;
    top: 0;
    right: 0;
    margin: auto;
    z-index: 1;
    background-image: linear-gradient(to right bottom, #fff, #e2e2e2);
    background-repeat: no-repeat;
    background-size: cover;
}

.video-player {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 2 !important;
}

/*
.video-player.active {
    display: block;
} */

.card:hover .preview__video {
    opacity: 100;
}

.preview__video {
    transition: opacity 300ms;
    opacity: 0;
}

.card__content {
    position: absolute;
    z-index: 2 !important;
    width: 100%;
    height: 40%;
    left: 0;
    right: 0;
    bottom: 0;
    padding-left: 9px;
    padding-right: 9px;
    color: #000;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    top: 200px;
    font-family: Arial, Helvetica, sans-serif;
}

.channel__video__name {
    font-size: 1.1em;
    margin-bottom: 10px;
    text-overflow: ellipsis;
    font-weight: bold;
    color: #494949;
    margin-top: 10px;
}

.channel__data {
    display: flex;
    align-items: center;
    width: 100%;
    gap: 10px;
    margin-bottom: 8px;
}

.channel__data__text {
    display: flex;
    flex-direction: column;
    gap: 5px;
    width: 100%;
}

.channel__subdata {
    display: flex;
    gap: 10px;
    color: #888888;
}

.channel__name {
    font-size: 0.8em;
    transition: color 400ms;
    margin-top: -5px;
    display: -webkit-box;
    -webkit-line-clamp: 1;
    line-clamp: 1;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
    max-height: calc(1.4em * 4);
    font-family: wenquanyimihei;
    font-size: 11px;
    margin-top: -8px !important;
    margin-left: 3px;
    margin-right: 20px;
}

.channel__views,
.channel__date {
    font-size: 0.8em;
    margin-top: -10px;
}

.card__view__data {
    position: absolute;
    bottom: 0;
    width: 100%;
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    padding: 8px;
    box-sizing: border-box;
    z-index: 10 !important;
}

.card__lenght,
.card__play__icon,
.card__view__preview {
    background: rgba(0, 0, 0, 0.5);
    padding: 5px;
    border-radius: 6px;
    font-size: 0.6em;
    font-weight: bold;
    color: rgba(255, 255, 255, 0.7);
    margin-top: 10px;
}

.card__play__icon {
    display: flex;
    justify-content: center;
    align-items: center;
    min-width: 30px;
    position: relative;
    cursor: pointer;
}

.card__view__preview {
    opacity: 0;
    transform: translateY(-20px);
    transition: opacity 900ms, transform 900ms;
}

.card:hover .card__view__preview {
    opacity: 100;
    transform: translateY(0px);
}

.card:nth-child(2n)::before {
    background-color: #ffd399;
    background-image: radial-gradient(at 15% 65%, hsla(249, 84%, 60%, 1) 0px, transparent 50%),
        radial-gradient(at 38% 24%, hsla(197, 80%, 66%, 1) 0px, transparent 50%),
        radial-gradient(at 65% 93%, hsla(228, 83%, 60%, 1) 0px, transparent 50%),
        radial-gradient(at 16% 58%, hsla(314, 83%, 72%, 1) 0px, transparent 50%),
        radial-gradient(at 77% 57%, hsla(298, 90%, 78%, 1) 0px, transparent 50%),
        radial-gradient(at 76% 95%, hsla(178, 64%, 76%, 1) 0px, transparent 50%),
        radial-gradient(at 15% 61%, hsla(248, 75%, 64%, 1) 0px, transparent 50%);
}

.card:nth-child(3n)::before {
    background-color: #99b3ff;
    background-image: radial-gradient(at 93% 72%, hsla(293, 71%, 70%, 1) 0px, transparent 50%),
        radial-gradient(at 28% 37%, hsla(252, 81%, 71%, 1) 0px, transparent 50%),
        radial-gradient(at 25% 59%, hsla(158, 67%, 62%, 1) 0px, transparent 50%),
        radial-gradient(at 48% 16%, hsla(197, 69%, 63%, 1) 0px, transparent 50%),
        radial-gradient(at 70% 56%, hsla(171, 69%, 70%, 1) 0px, transparent 50%),
        radial-gradient(at 7% 33%, hsla(65, 65%, 79%, 1) 0px, transparent 50%),
        radial-gradient(at 89% 22%, hsla(286, 64%, 72%, 1) 0px, transparent 50%);
}

.card:nth-child(4n)::before {
    background-color: #e2ff99;
    background-image: radial-gradient(at 22% 80%, hsla(265, 77%, 67%, 1) 0px, transparent 50%),
        radial-gradient(at 91% 77%, hsla(292, 60%, 78%, 1) 0px, transparent 50%),
        radial-gradient(at 66% 7%, hsla(46, 95%, 76%, 1) 0px, transparent 50%),
        radial-gradient(at 16% 32%, hsla(275, 90%, 75%, 1) 0px, transparent 50%),
        radial-gradient(at 26% 23%, hsla(283, 60%, 69%, 1) 0px, transparent 50%),
        radial-gradient(at 2% 64%, hsla(208, 62%, 62%, 1) 0px, transparent 50%),
        radial-gradient(at 62% 77%, hsla(112, 88%, 75%, 1) 0px, transparent 50%);
}

.card:nth-child(5n)::before {
    background-color: #99d1ff;
    background-image: radial-gradient(at 21% 94%, hsla(270, 95%, 76%, 1) 0px, transparent 50%),
        radial-gradient(at 56% 5%, hsla(252, 65%, 79%, 1) 0px, transparent 50%),
        radial-gradient(at 67% 94%, hsla(194, 81%, 67%, 1) 0px, transparent 50%),
        radial-gradient(at 15% 72%, hsla(304, 97%, 77%, 1) 0px, transparent 50%),
        radial-gradient(at 63% 56%, hsla(38, 98%, 63%, 1) 0px, transparent 50%),
        radial-gradient(at 41% 67%, hsla(321, 61%, 77%, 1) 0px, transparent 50%),
        radial-gradient(at 68% 68%, hsla(263, 67%, 69%, 1) 0px, transparent 50%);
}

.rating {
    position: absolute;
    top: -130px;
    right: 10px;
    background: rgba(0, 0, 0, 0.7);
    color: #fff;
    padding: 5px 10px;
    border-radius: 5px;
    font-size: 1.2em;
    font-weight: bold;
    z-index: 9999 !important;
    font-family: Arial, Helvetica, sans-serif;
}

.card__view {
    position: absolute;
    height: 55%;
    width: 100%;
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
}

.card__view:hover {
    transition: filter 1s;
    filter: blur(0px) brightness(1);
}

.rating-high {
    color: #ffd700;
}

.rating-good {
    color: #52c41a;
}

.rating-medium {
    color: #fa8c16;
}

.rating-low {
    color: #f5222d;
}

.recommend-tag {
    position: absolute;
    top: 10px;
    left: 10px;
    background: #ff4d4f;
    color: white;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 0.8em;
    z-index: 3;
}

.tags {
    display: flex;
    flex-wrap: wrap;
    gap: 4px;
    margin-top: -4px;
}

.tag {
    background: #f0f0f0;
    padding: 2px 8px;
    border-radius: 12px;
    font-size: 0.7em;
}

.description {
    color: #666;
    font-size: 0.95rem;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
    max-height: calc(1.4em * 4);
    font-family: wenquanyimihei;
    font-size: 11px;
    margin-top: -8px !important;
    margin-left: 3px;
    margin-right: 20px;
}

:deep(.video-js) {
    width: 100%;
    height: 100%;
}

:deep(.vjs-poster) {
    background-size: cover;
}

.video-player {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 3 !important;
}

.video-container {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 3 !important;
}

/* Video.js 自定义样式 */
:deep(.video-js) {
    width: 100%;
    height: 100%;
    position: relative;
    z-index: 3 !important;
}

:deep(.vjs-poster) {
    background-size: cover;
}

:deep(.vjs-big-play-button) {
    display: none;
    /* 隐藏默认的大播放按钮 */
}

/* 自定义播放按钮样式 */
.custom-controls {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background: rgba(0, 0, 0, 0.3);
    cursor: pointer;
    z-index: 2;
}

.play-button {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    background: rgba(0, 0, 0, 0.6);
    display: flex;
    justify-content: center;
    align-items: center;
    transition: transform 0.2s;
}

.play-button:hover {
    transform: scale(1.1);
}

/* 控制栏样式优化 */
:deep(.vjs-control-bar) {
    z-index: 4;
    background-color: rgba(0, 0, 0, 0.7);
    visibility: hidden;
    opacity: 0;
    transition: visibility 0.2s, opacity 0.2s;
}

.video-container:hover :deep(.vjs-control-bar) {
    visibility: visible;
    opacity: 1;
}

/* 播放时的样式 */
.video-container.active :deep(.vjs-control-bar) {
    visibility: visible;
    opacity: 1;
}

:deep(.vjs-volume-panel) {
    margin-right: 10px;
}

:deep(.vjs-volume-control.vjs-control.vjs-volume-vertical) {
    position: absolute;
    bottom: 3em;
    height: 8em;
    background-color: rgba(0, 0, 0, 0.7);
    border-radius: 4px;
    visibility: visible;
}

:deep(.vjs-volume-bar.vjs-slider-vertical) {
    height: 100px;
    margin: 0;
}

:deep(.vjs-volume-level) {
    background-color: #fff;
}

:deep(.vjs-mute-control) {
    width: 2em;
    cursor: pointer;
    margin-left: 10px;
}

/* 悬停时显示音量控制条 */
:deep(.vjs-volume-panel:hover .vjs-volume-control.vjs-volume-vertical) {
    visibility: visible;
    opacity: 1;
}

@keyframes moveUp {
    0% {
        transform: translateY(0);
    }

    100% {
        transform: translateY(-20px);
    }
}

.error-msg {
    background-color: rgba(0, 0, 0, 0.7);
    color: #fff;
    width: 150px;
    height: 30px;
    /* 建议显式设置高度 */
    display: flex;
    /* 启用 Flex 布局 */
    align-items: center;
    /* 垂直居中 */
    justify-content: center;
    /* 水平居中 */
    font-family: Arial, Helvetica, sans-serif;
    position: fixed;
    z-index: 4;
    left: 50%;
    transform: translateX(-50%);
    margin-top: 30px;
    animation: moveUp 0.4s ease-in-out forwards;
    font-size: 13px;
    border-radius: 8px;
}

.loading-overlay {
    position: fixed;
    top: 0;
    right: 0;
    width: 100%;
    height: 100%;
    background-color: #99d1ff;
    background-image: radial-gradient(at 21% 94%, hsla(270, 95%, 76%, 1) 0px, transparent 50%),
        radial-gradient(at 56% 5%, hsla(252, 65%, 79%, 1) 0px, transparent 50%),
        radial-gradient(at 67% 94%, hsla(194, 81%, 67%, 1) 0px, transparent 50%),
        radial-gradient(at 15% 72%, hsla(304, 97%, 77%, 1) 0px, transparent 50%),
        radial-gradient(at 63% 56%, hsla(38, 98%, 63%, 1) 0px, transparent 50%),
        radial-gradient(at 41% 67%, hsla(321, 61%, 77%, 1) 0px, transparent 50%),
        radial-gradient(at 68% 68%, hsla(263, 67%, 69%, 1) 0px, transparent 50%);
    /* 半透明背景 */
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 2;
    /* 确保遮罩层在最上层 */
}
</style>

<template>
    <div ref="scrollContainer" @scroll="handleScroll" class="game">
        <p v-if="showErrorpanle" class="error-msg">{{ errorPanleMsg }}</p>
        <div v-if="isShowLikePanel" class="loading-overlay">
            <Rating />
        </div>

        <div v-if="!isShowLikePanel" class="card">
            <div class="card__view" :style="{ backgroundImage: `url(${movieForm.cover})` }">
                <div @mouseenter="autoPlayVideo" @mouseleave="pauseVideo" class="background-image"
                    :style="{ backgroundImage: `url(${movieForm.cover})` }"></div>
                <div class="video-player" :class="{ active: isPlaying }">
                    <div class="video-container" :class="{ active: isPlaying }">
                        <video ref="videoPlayer" class="video-js vjs-default-skin" :poster="movieForm.cover"
                            preload="auto">
                            <source :src="movieForm.video" type="video/mp4">
                        </video>
                    </div>
                </div>
                <div class="card__view__data">
                    <p class="card__view__preview">预览</p>
                    <div class="rating" :class="ratingClass">{{ movieForm.rating.toFixed(1) }}</div>
                </div>
            </div>
            <div class="card__content">
                <div style="display: flex; justify-content: space-between;">
                    <div style="display: flex;">
                        <p class="channel__video__name">{{ movieForm.name }}</p>
                        <div style="margin-top: 30px;" class="channel__subdata">
                            <p style="margin-top: -16px;margin-left: 10px;" class="channel__date">{{
                                formatDate(movieForm.year) }}年</p>
                            <p style="margin-top: -16px;" class="channel__views">{{ formatViews(movieForm.users) }}人评价
                            </p>
                        </div>
                    </div>
                    <Like @click.stop="ShowLikePanel()" style="margin-top: 6px;margin-right: 15px;" />
                </div>
                <div class="channel__data">
                    <div class="channel__data__text">
                        <p style="color:#797979;" class="channel__name">导演: {{ movieForm.director }}</p>
                        <p style="margin-top: -5px;color: #797979;" class="channel__name">主演: {{ movieForm.actor }}</p>
                        <p class="description">
                            {{ movieForm.summary }}
                        </p>
                        <div class="tags">
                            <span :style="{ color: colorsRandom() }" v-for="tag in movieForm.types" :key="tag"
                                class="tag">{{
                                    tag }}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
import videojs from 'video.js';
import 'video.js/dist/video-js.css';
import Like from './Like.vue';
import Rating from './Rating.vue'

interface MovieForm {
    name: string;
    director: string;
    actor: string;
    year: Date;
    language: string;
    summary: string;
    cover: string;
    video: string;
    rating: number;
    types: string[];
    users: number;
}

const props = defineProps<{
    movieForm: MovieForm;
    isRecommended?: boolean;
}>();

const videoPlayer = ref<any>(null);
const player = ref<any>(null);
const isPlaying = ref(false);

onMounted(() => {
    // 初始化 Video.js
    player.value = videojs(videoPlayer.value, {
        controls: true,
        autoplay: false,
        preload: 'auto',
        fluid: true,
        responsive: true,
        playsinline: true,
        aspectRatio: '16:9',
        userActions: {
            hotkeys: true // 启用键盘快捷键
        },
        controlBar: {
            volumePanel: {
                inline: false,
                vertical: true // 垂直音量控制
            },
            children: [
                'playToggle',
                'volumePanel',
                'progressControl',
                'currentTimeDisplay',
                'timeDivider',
                'durationDisplay',
                'fullscreenToggle',
            ]
        }
    });

    // 监听播放状态
    player.value.on('play', () => {
        isPlaying.value = true;
    });

    player.value.on('pause', () => {
        isPlaying.value = false;
    });

    player.value.on('ended', () => {
        isPlaying.value = false;
    });
});

onBeforeUnmount(() => {
    if (player.value) {
        player.value.dispose();
    }
});

const ratingClass = computed(() => {
    if (props.movieForm.rating >= 9) return 'rating-high';
    if (props.movieForm.rating >= 8) return 'rating-good';
    if (props.movieForm.rating >= 6) return 'rating-medium';
    return 'rating-low';
});

const toggleVideo = () => {
    if (!player.value) return;

    if (player.value.paused()) {
        player.value.play();
    } else {
        player.value.pause();
    }
};

const autoPlayVideo = () => {
    if (!player.value) return;

    player.value.play()
        .then(() => {
            isPlaying.value = true;
        })
        .catch((error: any) => {
            console.error("视频播放失败:", error);
            isPlaying.value = false;
        });
};

const pauseVideo = () => {
    if (!player.value) return;

    player.value.pause();
    isPlaying.value = false;
};

const formatViews = (views: number) => {
    if (views >= 1e8) return (views / 1e8).toFixed(1) + '亿';
    if (views >= 1e4) return (views / 1e4).toFixed(1) + '万';
    return views;
};

const formatDate = (date: Date) => {
    return new Date(date).getFullYear();
};

const colors = [
    "#ec8236",
    "#f56c6c",
    "#67c23a",
    "#e6a23c",
    "#409eff",
    "#c239e6",
    "#5941d3",
];
const colorsRandom = () => colors[Math.floor(Math.random() * colors.length)];
const showErrorpanle = ref(false);
const errorPanleMsg = ref("");
// 展示喜欢面板
const isShowLikePanel = ref(false);
const ShowLikePanel = () => {
    isShowLikePanel.value = true;
};

const scrollContainer = ref(null);

const handleScroll = () => {
    if (!scrollContainer.value) return;

    const { scrollTop, scrollHeight, clientHeight } = scrollContainer.value;
    const isBottom = scrollTop + clientHeight >= scrollHeight - 5;

    if (isBottom) {
        console.log("Reached bottom");
    }
};
</script>
