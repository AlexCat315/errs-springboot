<style scoped>
.main {
    background-color: white;
    padding: 1em;
    padding-bottom: 1.1em;
    border-radius: 15px;
    margin: 1em;
    width: 100%;
    box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
}

.loader {
    display: flex;
    flex-direction: row;
    height: 4em;
    padding-left: 1em;
    padding-right: 1em;
    justify-content: left;
    border-radius: 10px;
    transition: 0.3s ease-in-out;
}

.loader:hover {
    cursor: pointer;
    background-color: lightgray;
}

.currentplaying {
    display: flex;
    margin: 1em;
}

.spotify {
    width: 50px;
    height: 50px;
    margin-right: 0.6em;
}

.heading {
    color: black;
    font-size: 1.1em;
    font-weight: bold;
    align-self: center;
    margin-top: 2px;
    margin-left: 5px;
}

.loading {
    display: flex;
    margin-left: 0.3em;
}

.load {
    width: 4px;
    height: 33px;
    background-color: limegreen;
    border-radius: 5px;
}

.load.playing {
    animation: 1s move6 infinite;
}

.load.playing:nth-child(1) {
    animation-delay: 0.2s;
}

.load.playing:nth-child(2) {
    animation-delay: 0.4s;
}

.load.playing:nth-child(3) {
    animation-delay: 0.6s;
}

.play {
    position: relative;
    left: 0.35em;
    height: 1.6em;
    width: 40px;
    clip-path: polygon(50% 50%, 100% 50%, 75% 6.6%);
    background-color: black;
    transform: rotate(90deg);
    align-self: center;
    margin-top: -0.7em;
    justify-self: center;
}

.albumcover {
    position: relative;
    margin-right: 1em;
    height: 40px;
    width: 40px;
    background-size: cover;
    background-position: center;
    align-self: center;
    border-radius: 5px;
    margin-left: 20px;
}

.song {
    position: relative;
    margin-right: 1em;
    color: black;
    align-self: center;
}

.artist {
    font-size: 0.6em;
}

@keyframes move6 {
    0% {
        height: 0.2em;
    }

    25% {
        height: 0.7em;
    }

    50% {
        height: 1.5em;
    }

    100% {
        height: 0.2em;
    }
}
</style>

<template>
    <div class="main">
        <div class="currentplaying">
            <img style="width: 30px; height: 30px" :src="props.icon" />
            <p class="heading">{{ props.iconName }}</p>
        </div>
        <div v-for="(song, index) in songs" :key="index" class="loader">
            <div @click="currentSong = null" v-if="currentSong === song" class="loading">
                <div class="load" :class="{ playing: !audio?.paused }"></div>
                <div class="load" :class="{ playing: !audio?.paused }"></div>
                <div class="load" :class="{ playing: !audio?.paused }"></div>
                <div class="load" :class="{ playing: !audio?.paused }"></div>
                <div class="load" :class="{ playing: !audio?.paused }"></div>
            </div>
            <div @click="playSong(song)" v-else class="play"></div>
            <div class="albumcover" :style="{ backgroundImage: `url(${song.coverUrl})` }"></div>
            <div class="song">
                <div style="display: flex">
                    <p class="name">{{ song.name }}</p>
                </div>
                <p style="margin-top: -15px" class="artist">
                    {{ song.artist }}
                </p>
            </div>
            <Rating :score="song.score" :users="song.users" style="margin-left:auto" />

            <Like @click="sendMessage" style="margin-top: 11px;" />
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from "vue";
import { get_music_all } from "../../../../net/music/get";
import Like from "./Like.vue";
import Rating from "./Rating.vue";
import { forEach } from "lodash";

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

const songs = ref<Song[]>([]);
const currentSong = ref<Song | null>(null);
const audio = ref<HTMLAudioElement | null>(null);

function formatNumber(num: number) {
    if (num >= 1000000) {
        return (num / 1000000).toFixed(1) + "m+";
    } else if (num >= 10000) {
        return (num / 10000).toFixed(1) + "w+";
    } else if (num >= 1000) {
        return (num / 1000).toFixed(1) + "k+";
    }
    return num.toString();
}
onMounted(async () => {
    try {
        audio.value = new Audio();
        get_music_all(
            1,
            10,
            (data: any) => {
                console.log(data);
                // 随机排序
                songs.value = data.data
                    .slice(0, 5)
                    .sort(() => Math.random() - 0.5);
                forEach(songs.value, (song) => {
                    song.users = formatNumber(Number(song.users));
                });
            },
            (err: string) => {
                console.log(err);
            },
        );
    } catch (error) {
        console.error("Failed to fetch songs:", error);
    }
});

watch(
    () => currentSong.value,
    (newSong, oldSong) => {
        if (newSong) {
            audio.value!.src = newSong.audioUrl;
            audio.value!.play();
        } else if (oldSong) {
            audio.value!.pause();
        }
    },
    { immediate: true },
);

const playSong = (song: Song) => {
    if (!audio.value) {
        audio.value = new Audio();
    }

    if (currentSong.value === song) {
        if (audio.value.paused) {
            audio.value.play();
        } else {
            audio.value.pause();
        }
    } else {
        if (currentSong.value) {
            audio.value.pause();
        }
        currentSong.value = song;
        audio.value.src = song.audioUrl;
        audio.value.play();
    }
};

const props = defineProps<{
    icon?: string;
    iconName: string;
}>();

// 定义触发的事件和参数类型
const emit = defineEmits<{
    (event: "updateShowLikePanel", newMessage: boolean): void;
}>();

// 通过点击按钮向父组件传递消息
const sendMessage = () => {
    emit("updateShowLikePanel", true);
};
</script>
