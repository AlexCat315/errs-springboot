<script lang="ts" setup>
import { ref, onMounted } from "vue";
import Card from "./components/Card.vue";
import MusicPlay from "./components/MusicPlay.vue";
import LikePanel from "./components/LikePanle.vue";
const showErrorpanle = ref(false);
const errorPanleMsg = ref("");
const showLikePanel = ref(false);
const handleUpdateShowLikePanel = (value: boolean) => {
    showLikePanel.value = value;
};
interface Song {
    id: number;
    name: string;
    artist: string;
    coverUrl: string;
    audioUrl: string;
    tags: string[];
    users: string;
    score: number;
}
import { onUnmounted } from "vue";

const audioPlayer = ref<HTMLAudioElement>(new Audio());
const currentSong = ref<Song | null>(null);
const currentPlaylist = ref<Song[]>([]); // 当前正在播放的列表
const currentIndex = ref(-1);
const isPlaying = ref(false); // 添加播放状态

// 播放上一首
const playPrevious = () => {
    if (currentPlaylist.value.length === 0) return;

    currentIndex.value = currentIndex.value <= 0
        ? currentPlaylist.value.length - 1
        : currentIndex.value - 1;

    currentSong.value = currentPlaylist.value[currentIndex.value];
    audioPlayer.value.src = currentSong.value.audioUrl;
    audioPlayer.value.play();
};

// 播放下一首
const playNext = () => {
    if (currentPlaylist.value.length === 0) return;

    currentIndex.value = currentIndex.value >= currentPlaylist.value.length - 1
        ? 0
        : currentIndex.value + 1;

    currentSong.value = currentPlaylist.value[currentIndex.value];
    audioPlayer.value.src = currentSong.value.audioUrl;
    audioPlayer.value.play();
};

// handlePlaySong 函数
const handlePlaySong = (songData: { song: Song, playlist: Song[] }) => {
    currentPlaylist.value = songData.playlist;
    currentIndex.value = currentPlaylist.value.findIndex(s => s.id === songData.song.id);
    currentSong.value = songData.song;
    audioPlayer.value.src = songData.song.audioUrl;
    audioPlayer.value.play();
    isPlaying.value = true;
};

const handleClose = () => {
    // 先移除错误监听器
    const errorHandler = audioPlayer.value.onerror;
    audioPlayer.value.onerror = null;
    
    // 暂停播放
    audioPlayer.value.pause();
    // 清除音频源
    // audioPlayer.value.src = '';
    
    // 重置状态
    isPlaying.value = false;
    currentSong.value = null;
    currentIndex.value = -1;
    
    // 恢复错误监听器
    setTimeout(() => {
        audioPlayer.value.onerror = errorHandler;
    }, 0);
};

onUnmounted(() => {
    audioPlayer.value.pause();
    audioPlayer.value.src = '';
});

onMounted(() => {
    // 添加音频播放状态监听
    audioPlayer.value.addEventListener('play', () => {
        isPlaying.value = true;
    });

    audioPlayer.value.addEventListener('pause', () => {
        isPlaying.value = false;
    });

    audioPlayer.value.addEventListener('ended', () => {
        playNext();
    });

    audioPlayer.value.addEventListener('error', (e) => {
        console.error('Audio error:', e);
        showErrorpanle.value = true;
        errorPanleMsg.value = "音频加载失败";
    });
});

const cardList = [
    {
        icon: 'https://www.alexcat.it.com/minio-api/public-errs/网易云音乐.png',
        iconName: '网易云音乐'
    },
    {
        icon: 'https://www.alexcat.it.com/minio-api/public-errs/QQ音乐.png',
        iconName: 'QQ音乐'
    },
];
</script>

<template>
    <div>
        <p v-if="showErrorpanle" class="error-msg">{{ errorPanleMsg }}</p>
        <div v-if="showLikePanel" class="loading-overlay">
            <LikePanel @updateShowLikePanel="handleUpdateShowLikePanel" />
        </div>

        <!-- 使用grid布局来实现每行两个卡片 -->
        <div class="card-container">
            <Card v-for="card in cardList" :key="card.iconName" :isPlaying="isPlaying" :currentPlayingSong="currentSong"
                :icon="card.icon" :iconName="card.iconName" @play-song="handlePlaySong"
                @updateShowLikePanel="handleUpdateShowLikePanel" />
        </div>

        <div class="bottom-player" v-if="currentSong">
            <MusicPlay @close="handleClose" :song="currentSong" :audioPlayer="audioPlayer" @previous="playPrevious"
                @next="playNext" />
        </div>
    </div>
</template>

<style scoped>
.card-container {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    /* 每行两列 */
    gap: 20px;
    /* 卡片之间的间距 */
    margin: 10px 30px 10px 10px;
    /* 上 右 下 左 */
    font-family: Arial, Helvetica, sans-serif;
}

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

.bottom-player {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    background-color: #282828;
    z-index: 1000;
    padding: 10px 0;
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
