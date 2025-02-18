<style scoped>
.main {
    background-color: white;
    padding: 1em;
    padding-bottom: 1.1em;
    border-radius: 15px;
    margin: 1em;
    width: 90%;
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
    transition: opacity 0.3s ease-in-out;
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
    transition: opacity 0.3s ease-in-out;
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
        <div @click="playSong(song)" v-for="(song, index) in songs" :key="index" class="loader">
            <h3 style="margin-top: 25px;">{{ index + 1 }}</h3>
            <div v-if="isSongPlaying(song)" class="loading">
                <div class="load playing"></div>
                <div class="load playing"></div>
                <div class="load playing"></div>
                <div class="load playing"></div>
                <div class="load playing"></div>
            </div>
            <div v-else class="play"></div>
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
            <Like  @click.stop="sendMessage(song)" style="margin-top: 11px;" />
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch,inject,Ref } from "vue";
import { get_music_search } from "../../../../net/music/get";
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
    iconSource?: string;
}

const songs = ref<Song[]>([]);
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

const emit = defineEmits<{
    (event: "updateShowLikePanel", data: [Song, boolean]): void;
    (event: "play-song", data: { song: Song; playlist: Song[] }): void;
}>();
const globalSearch = inject("globalSearch") as Ref<string>;
if (!globalSearch) {
    throw new Error("globalSearch is not provided");
}
onMounted(async () => {
    try {
        audio.value = new Audio();
        get_music_search(
            1,
            10,
            globalSearch.value,
            (data: any) => {
              console.log('data', data);
                songs.value = data.data
                    .sort(() => Math.random() - 0.5)
                    .map(song => ({
                        ...song,
                        iconSource: props.iconName
                    }));
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

watch(() => globalSearch.value, () => {
  songs.value = [];
  console.log('globalSearch changed', globalSearch.value);
  try {
      audio.value = new Audio();
      get_music_search(
          1,
          10,
          globalSearch.value,
          (data: any) => {
            console.log('data', data);
              songs.value = data.data
                  .sort(() => Math.random() - 0.5)
                  .map(song => ({
                      ...song,
                      iconSource: props.iconName
                  }));
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


// 修改播放状态判断逻辑
const isSongPlaying = (song: Song) => {
    return props.currentPlayingSong?.id === song.id && 
           props.currentPlayingSong?.iconSource === props.iconName && 
           props.isPlaying;
};

// playSong 函数也需要确保传递 iconSource
const playSong = (song: Song) => {
    emit("play-song", {
        song: { ...song, iconSource: props.iconName },
        playlist: songs.value
    });
};

const props = defineProps<{
    icon?: string;
    iconName: string;
    currentPlayingSong?: Song | null;
    isPlaying: boolean;
}>();

const sendMessage = (song:Song) => {
    emit("updateShowLikePanel",[song, true]);
};
</script>