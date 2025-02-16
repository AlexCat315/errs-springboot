<script setup lang="ts">
import { ref,  onMounted } from 'vue';
import { get_move_all } from '../...../../../../net/movie/get';
import VideoCard from './components/VideoCard.vue';
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
const movies = ref<MovieForm[]>([]);


onMounted(() => {
    get_move_all(
        1, 10, (data: any) => {
            movies.value = data.data;
            console.log(data.data)
        }, (error: string) => {
            console.log(error)
        }

    );
});
</script>

<template>
    <div class="recommend-grid">
        <VideoCard v-for="movie in movies" :key="movie.name" :movieForm="movie" :isRecommended="movie.rating >= 9"
            style="margin-top: 30px;" />
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
    margin-top: 30px;
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
</style>
