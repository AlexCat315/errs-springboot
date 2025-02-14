<script lang="ts" setup>
import { ref } from 'vue';
import Card from "./components/Card.vue";
import MusicPlay from "./components/MusicPlay.vue";
import LikePanel from "./components/LikePanle.vue";

const showErrorpanle = ref(false);
const errorPanleMsg = ref("");
const showLikePanel = ref(false);
const handleUpdateShowLikePanel = (value: boolean) => {
    showLikePanel.value = value;
};
</script>

<template>
    <div>
        <p v-if="showErrorpanle" class="error-msg">{{ errorPanleMsg }}</p>
        <div v-if="showLikePanel" class="loading-overlay">
            <LikePanel @updateShowLikePanel="handleUpdateShowLikePanel" />
        </div>
        <!-- Arranged horizontally, each occupies 50% -->
        <!-- margin 10px -->
        <div
            style="
                display: flex;
                justify-content: space-between;
                margin: 10px;
                font-family: Arial, Helvetica, sans-serif;
            "
        >
            <Card
                :icon="'https://www.alexcat.it.com/minio-api/public-errs/网易云音乐.png'"
                :iconName="'网易云音乐'"
            />
            <Card
                :icon="'https://www.alexcat.it.com/minio-api/public-errs/QQ音乐.png'"
                :iconName="'QQ音乐'"
            />
        </div>
        <MusicPlay />
    </div>
</template>

<style scoped>
.loading-overlay {
    position: fixed;
    top: 0;
    right: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(58, 58, 58, 0.8);
    /* 半透明背景 */
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 10;
    /* 确保遮罩层在最上层 */
}
</style>
