<script setup lang="ts">
import { ref, inject } from 'vue';
import VideoList from './video/components/VideoList.vue';
import Rating from "../videos/components/Rating.vue"

const globalTheme = inject<string>("globalTheme");


const updateMovieValue = (move_id: number) => {
    console.log(move_id)
    movieId.value = move_id;
    isShowLikePanel.value = true;
}

// 添加不同类型
enum ListType {
    BOOK = "book",
    GAMES = "games",
    MUSIC = "music",
    MOVIE = "movie",
}

const currentTab = ref<ListType>(ListType.BOOK);
const tabs = {
    [ListType.BOOK]: { title: "书籍", apiParam: 0 },
    [ListType.GAMES]: { title: "游戏", apiParam: 1 },
    [ListType.MUSIC]: { title: "音乐", apiParam: 2 },
    [ListType.MOVIE]: { title: "电影", apiParam: 3 },
};
const switchTab = (tab: ListType) => {
    currentTab.value = tab;
}

const movieId = ref(0)
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
const handleCancel = () => {
    isShowLikePanel.value = false;
};

</script>

<template>
    <div class="container">
        <p v-show="showErrorpanle" class="error-msg">{{ errorPanleMsg }}</p>
        <div v-if="isShowLikePanel" class="loading-overlay">
            <Rating :movieId="movieId" @cancel="handleCancel" @liked="ShowLikePanel" />
        </div>
        <div v-if="!isShowLikePanel" class="section-header">
            <div style="display: flex; align-items: center">
                <p :style="{
                    color: globalTheme === 'dark' ? '#FFF' : 'black',
                }" style="
                    font-size: 23px;
                    font-family: yousu-title-black;
                    margin-left: 30px;
                    margin-top: 30px;
                ">
                    {{ tabs[currentTab].title }}
                </p>
                <div style="display: flex; margin-left: 20px; margin-top: 30px">
                    <button v-for="(tab, key) in tabs" :key="key" @click="switchTab(key as ListType)" :style="{
                        color: currentTab === key ? '#409eff' : '#8a8a8a',
                        fontWeight: currentTab === key ? 'bold' : 'normal',
                        textDecoration:
                            currentTab === key ? 'underline' : 'none',
                        marginRight: '15px',
                        background: 'none',
                        border: 'none',
                        cursor: 'pointer',
                    }">
                        {{ tab.title }}
                    </button>
                </div>
            </div>
            <VideoList class="list" @update:modelValue="updateMovieValue" v-if="currentTab === ListType.MOVIE" />

        </div>
    </div>
</template>

<style scoped>
.container {
    display: flex;
    flex-direction: column;
    height: 100vh;
    /* 或者使用其他固定高度 */
}

.section-header {
    flex-shrink: 0;
    /* 防止header被压缩 */
}

.list {
    flex: 1;
    /* 占据剩余所有空间 */
    overflow-y: auto;
    /* 如果内容超出则显示滚动条 */
    padding-left: 10px;
    padding-right: 10px;
    font-family: Arial, Helvetica, sans-serif;
}
</style>
