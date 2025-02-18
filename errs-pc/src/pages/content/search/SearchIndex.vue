<script setup lang="ts">
import { ref, inject } from 'vue';
import VideoList from './video/components/VideoList.vue';
import Rating from "../videos/components/Rating.vue"
import BookList from './book/BookList.vue';
import MusicList from './music/MusicList.vue';
import GamesList from './games/GamesList.vue';

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
            <BookList class="list" v-if="currentTab === ListType.BOOK" />
            <MusicList class="list" v-if="currentTab === ListType.MUSIC" />
            <GamesList class="list" v-if="currentTab === ListType.GAMES" />
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
    max-width: 100%;
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
</style>
