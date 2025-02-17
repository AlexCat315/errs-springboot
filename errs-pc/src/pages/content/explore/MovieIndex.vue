<script setup lang="ts">
import { ref, onMounted, inject } from 'vue';
import { get_move_all } from '../...../../../../net/movie/get';
import VideoCard from './components/movie/VideoCard.vue';
import Rating from "./components/movie/Rating.vue"
import ViewAll from "./components/movie/ViewAll.vue";

interface MovieForm {
    id: number
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
const movies = ref<MovieForm[]>([]);
const scrollContainer = ref(null);

const handleScroll = () => {
    if (!scrollContainer.value) return;

    const { scrollTop, scrollHeight, clientHeight } = scrollContainer.value;
    const isBottom = scrollTop + clientHeight >= scrollHeight - 5;

    if (isBottom) {
        console.log("Reached bottom");
    }
};

onMounted(() => {
    get_move_all(
        1,6, (data: any) => {
            movies.value = data.data;
            console.log(data.data)
        }, (error: string) => {
            console.log(error)
        }

    );
});

const showErrorpanle = ref(false);
const errorPanleMsg = ref("");
// 展示喜欢面板
const isShowLikePanel = ref(false);
const ShowLikePanel = (value: true) => {
    isShowLikePanel.value = value;
    showErrorpanle.value = true;
    errorPanleMsg.value = "评论成功";
    setTimeout(() => {
        isShowLikePanel.value = false;
        showErrorpanle.value = false;
    }, 2000);
};
const movieId = ref(0);
const ShowLikeVideoPanel = (value: [boolean, number]) => {
    isShowLikePanel.value = value[0];
    movieId.value = value[1];
};
const globalTheme = inject<string>("globalTheme");

if (globalTheme === undefined) {
    throw new Error("Function not implemented.");
}

const globalSelect = inject("globalSelect");
</script>

<template>
    <div ref="scrollContainer" @scroll="handleScroll">
        <p v-show="showErrorpanle" class="error-msg">{{ errorPanleMsg }}</p>
        <div v-show="isShowLikePanel" class="loading-overlay">
            <Rating :movieId="movieId" @liked="ShowLikePanel" />
        </div>
        <div class="section-header">
            <p :style="{
                color: globalTheme === 'dark' ? '#FFF' : 'black',
            }" style="
                    font-size: 23px;
                    font-family: yousu-title-black;
                    margin-left: 30px;
                    margin-top: 30px;
                ">
                闻名中外
            </p>
            <ViewAll @Click="globalSelect = 5" style="margin-top: 25px" class="view-all" />
        </div>
        <div v-show="!isShowLikePanel" class="recommend-grid">
            <VideoCard @liked="ShowLikeVideoPanel" v-for="movie in movies" :key="movie.name" :movieForm="movie"
                :isRecommended="movie.rating >= 9" style="margin-top: 30px;" />
        </div>
    </div>
</template>

<style scoped>
.recommend-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 8px;
    /* 缩小间距 */
    padding: 0 8px;
    /* 添加容器内边距 */
    max-width: 1200px;
    /* 限制最大宽度 */
    margin: 0 auto;
    /* 居中显示 */
    margin-left: 60px;
    margin-top: -30px;
    padding-top: 30px;
}

/* 卡片尺寸控制 */
.recommend-grid>* {
    min-width: 0;
    /* 防止内容溢出 */
}

/* 响应式调整 */
@media (min-width: 768px) {
    .recommend-grid {
        gap: 12px;
        padding: 0 12px;
    }
}

@media (max-width: 480px) {
    .recommend-grid {
        grid-template-columns: 1fr;
        padding: 0 8px;
    }
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

.section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

}

.view-all {
    font-family: Arial, Helvetica, sans-serif;
    text-decoration: none;
    font-size: 11px;
    height: 28px;
    width: 120px;
    margin-top: -60px;
    /* 缩放 */
    transform: scale(0.77);
    font-size: 15px;
    margin-right: 20px;
}
</style>
