<script setup lang="ts">

import VideoCard from './components/VideoCard.vue';

</script>

<template>
    <div class="recommend-grid">
        <VideoCard v-for="i in 6" :key="i" style="margin-top: 30px;" />
    </div>
</template>

<style scoped>
.recommend-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 8px; /* 缩小间距 */
    padding: 0 8px; /* 添加容器内边距 */
    max-width: 1200px; /* 限制最大宽度 */
    margin: 0 auto; /* 居中显示 */
    margin-left: 60px;
    margin-top: 30px;
    padding-top: 30px;
}

/* 卡片尺寸控制 */
.recommend-grid > * {
    min-width: 0; /* 防止内容溢出 */
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