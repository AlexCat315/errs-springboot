<style scoped>
/* Spotify music card made by: csozi | Website: www.csozi.hu*/
.card-wrapper {
    position: relative;
}

.card-bg {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 0;
}

.card-content {
    position: relative;
    z-index: 1;
}

.card {
    position: relative;
    width: 100% -440px;
    height: 80px;
    border-radius: 10px;
    margin-left: 240px;

}



.top {
    position: relative;
    width: 100%;
    display: flex;
    gap: 10px;
    margin-left: 50px;
}

.pfp {
    position: relative;
    top: 5px;
    left: 5px;
    height: 40px;
    width: 40px;
    background-color: #d2d2d2;
    border-radius: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.title-1 {
    color: white;
    font-size: 25px;
    font-weight: bolder;
    margin-left: 5px;
    margin-top: 2px;
    font-family: Arial, Helvetica, sans-serif;
}

.title-2 {
    color: lightgrey;
    font-size: 12px;
    font-weight: bold;
    margin-top: -20px;
    margin-left: 5px;
}

.time {
    width: 90%;
    background-color: #5e5e5e;
    height: 6px;
    border-radius: 3px;
    position: absolute;
    left: 5%;
    bottom: 5px;
}

.elapsed {
    width: 42%;
    background-color: #1db954;
    height: 100%;
    border-radius: 3px;
}

.controls {
    color: white;
    display: flex;
    position: absolute;
    bottom: 30px;
    left: 0;
    width: 100%;
    justify-content: center;
    margin-left: -20px;
}

.volume {
    height: 100%;
    width: 50px;
    margin-right: 2px;
}

.air {
    height: 100%;
    width: 54px;
}

.controls svg {
    cursor: pointer;
    transition: 0.1s;
    margin-bottom: 5px;
}

.controls svg:hover {
    color: #1db954;
}

.volume {
    opacity: 0;
    position: relative;
    transition: 0.2s;
}

.volume .slider {
    height: 4px;
    background-color: #5e5e5e;
    width: 80%;
    border-radius: 2px;
    margin-top: 8px;
    margin-left: 10%;
}

.volume .slider .green {
    background-color: #1db954;
    height: 100%;
    width: 80%;
    border-radius: 3px;
}

.volume .circle {
    background-color: white;
    height: 6px;
    width: 6px;
    border-radius: 3px;
    position: absolute;
    right: 20%;
    top: 60%;
}

.volume_button:hover~.volume {
    opacity: 1;
}

.timetext {
    color: white;
    font-size: 8px;
    position: absolute;
}

.time_now {
    bottom: -5px;
    left: 15px;
    color: #1db954;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: 2000;
}

.time_full {
    bottom: -5px;
    right: 14px;
    font-family: Arial, Helvetica, sans-serif;
}

.playing {
    display: flex;
    position: relative;
    justify-content: center;
    gap: 1px;
    width: 30px;
    height: 20px;
}

.greenline {
    background-color: #1db954;
    height: 20px;
    width: 2px;
    position: relative;
    transform-origin: bottom;
}

.line-1 {
    animation: infinite playing 1s ease-in-out;
    animation-delay: 0.2s;
}

.line-2 {
    animation: infinite playing 1s ease-in-out;
    animation-delay: 0.5s;
}

.line-3 {
    animation: infinite playing 1s ease-in-out;
    animation-delay: 0.6s;
}

.line-4 {
    animation: infinite playing 1s ease-in-out;
    animation-delay: 0s;
}

.line-5 {
    animation: infinite playing 1s ease-in-out;
    animation-delay: 0.4s;

}

.time {
    cursor: pointer;
    position: relative;
}

.time:hover .elapsed {
    background-color: #1ed760;
    /* 鼠标悬停时的颜色 */
}

.elapsed {
    transition: width 0.1s ease-out;
}

.close-button {
    position: absolute;
    top: 5px;
    right: 20px;
    cursor: pointer;
    z-index: 2;
    transition: transform 0.2s;
}

.close-button:hover {
    transform: scale(1.1);
    opacity: 0.8;
}

@keyframes playing {
    0% {
        transform: scaleY(0.1);
    }

    33% {
        transform: scaleY(0.6);
    }

    66% {
        transform: scaleY(0.9);
    }

    100% {
        transform: scaleY(0.1);
    }
}
</style>

<template>
    <div class="card-wrapper">
        <div class="card-bg" :style="{
            backgroundImage: `url(${song.coverUrl})`,
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            filter: 'blur(4px) brightness(0.4)'
        }"></div>
        <div class="card">
            <!-- 关闭播放器 -->
            <svg @click="closePlayer" class="close-button" t="1739606074432" viewBox="0 0 1024 1024" version="1.1"
                xmlns="http://www.w3.org/2000/svg" p-id="11579" width="20" height="20">
                <path
                    d="M512 65c246.871 0 447 200.129 447 447S758.871 959 512 959 65 758.871 65 512 265.129 65 512 65z m-95.395 290.802c-19.493-18.96-48.381-21.052-64.87-4.563-16.656 16.655-14.353 45.962 5.143 65.458l94.807 95.183-95.303 94.927-0.58 0.588c-18.96 19.493-21.052 48.381-4.563 64.87 16.655 16.656 45.962 14.353 65.458-5.143l95.183-94.808 94.927 95.304 0.588 0.58c19.493 18.96 48.381 21.052 64.87 4.563 16.656-16.655 14.353-45.962-5.143-65.458l-94.807-95.184 95.303-94.926 0.58-0.588c18.96-19.493 21.052-48.381 4.563-64.87-16.655-16.656-45.962-14.353-65.458 5.143l-95.183 94.806-94.927-95.302z"
                    fill="#FFF" p-id="11580"></path>
            </svg>
            <div class="top">
                <div class="pfp">
                    <!-- 音乐封面和播放动画 -->
                    <div class="playing" :style="{
                        backgroundImage: `url(${song.coverUrl})`,
                        backgroundSize: 'cover',
                        backgroundPosition: 'center'
                    }">
                        <div class="greenline line-1"></div>
                        <div class="greenline line-2"></div>
                        <div class="greenline line-3"></div>
                        <div class="greenline line-4"></div>
                        <div class="greenline line-5"></div>
                    </div>
                </div>
                <div class="texts">
                    <p class="title-1">{{ song.name }}</p>
                    <p class="title-2">{{ song.artist }}</p>
                </div>
            </div>

            <div class="controls">
                <!-- 音量控制 -->
                <!-- 非禁音svg -->
                <svg v-if="showVolume" @click="toggleMute" style="margin-top: 6px;" class="volume_button" width="24"
                    height="20" fill="currentColor" viewBox="0 0 24 24">
                    <path fill-rule="evenodd"
                        d="M11.26 3.691A1.2 1.2 0 0 1 12 4.8v14.4a1.199 1.199 0 0 1-2.048.848L5.503 15.6H2.4a1.2 1.2 0 0 1-1.2-1.2V9.6a1.2 1.2 0 0 1 1.2-1.2h3.103l4.449-4.448a1.2 1.2 0 0 1 1.308-.26Zm6.328-.176a1.2 1.2 0 0 1 1.697 0A11.967 11.967 0 0 1 22.8 12a11.966 11.966 0 0 1-3.515 8.485 1.2 1.2 0 0 1-1.697-1.697A9.563 9.563 0 0 0 20.4 12a9.565 9.565 0 0 0-2.812-6.788 1.2 1.2 0 0 1 0-1.697Zm-3.394 3.393a1.2 1.2 0 0 1 1.698 0A7.178 7.178 0 0 1 18 12a7.18 7.18 0 0 1-2.108 5.092 1.2 1.2 0 1 1-1.698-1.698A4.782 4.782 0 0 0 15.6 12a4.78 4.78 0 0 0-1.406-3.394 1.2 1.2 0 0 1 0-1.698Z"
                        clip-rule="evenodd"></path>
                </svg>
                <!-- 禁音svg -->
                <svg v-else @click="toggleMute" t="1739601630153" style="margin-top: 8px;" class="volume_button"
                    width="17" height="17" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                    p-id="7937">
                    <path
                        d="M909.312 835.584c72.192-89.088 113.664-202.24 113.664-323.584 0-194.56-105.984-369.152-274.432-455.168-25.088-15.36-55.808-5.632-71.168 20.48-9.728 25.088 0 55.808 25.088 71.168 133.12 66.56 219.648 204.288 214.016 368.128 0 93.184-28.16 178.688-79.36 247.296L754.688 680.96c36.352-50.176 55.808-111.104 55.808-175.104 0-87.552-35.84-168.448-102.4-224.768-20.48-15.36-51.2-15.36-71.168 5.632-20.48 20.48-20.48 55.808 0 71.168 46.08 40.448 71.168 91.648 71.168 153.088 0 36.352-8.704 69.12-25.6 98.304l-164.352-164.352V153.6c1.024-46.08-45.056-66.56-80.896-41.472L296.96 223.232 91.136 16.896C71.168-3.072 37.888-3.072 17.92 16.896c-19.968 19.968-19.968 53.248 0 73.216l197.632 197.632-24.064 18.944H104.96c-55.808 0-102.4 46.08-102.4 102.4v204.288c0 55.808 46.08 102.4 102.4 102.4h86.528l244.736 194.56c30.72 25.088 81.92 0 81.92-40.448v-279.552l103.424 103.424c-0.512 12.8 4.608 25.088 15.36 30.208 7.168 10.752 20.48 17.92 33.792 18.944l94.208 94.208c-20.48 15.872-43.52 30.208-67.584 41.984-25.088 9.728-35.84 40.448-20.48 66.56 15.36 25.088 46.08 35.84 71.168 20.48 31.744-16.384 61.44-35.84 88.576-57.856l97.28 97.28c19.968 19.968 53.248 19.968 73.216 0s19.968-53.248 0-73.216l-97.792-96.768z"
                        fill="#FFF" p-id="7938"></path>
                </svg>
                <!-- 调节音量滑块 -->
                <div class="volume" style="margin-top: 6px;" :style="{ opacity: showVolume ? 1 : 0 }">
                    <div class="slider" @mousedown="adjustVolume">
                        <div class="green" :style="{ width: `${volume}%` }"></div>
                    </div>
                    <div class="circle" :style="{ right: `${100 - volume}%` }"></div>
                </div>

                <!-- 控制按钮 -->
                <svg style="margin-top: 4px;" @click="previousTrack" width="24" height="24" fill="currentColor"
                    viewBox="0 0 24 24">
                    <path fill-rule="evenodd"
                        d="M12 21.6a9.6 9.6 0 1 0 0-19.2 9.6 9.6 0 0 0 0 19.2Zm.848-12.352a1.2 1.2 0 0 0-1.696-1.696l-3.6 3.6a1.2 1.2 0 0 0 0 1.696l3.6 3.6a1.2 1.2 0 0 0 1.696-1.696L11.297 13.2H15.6a1.2 1.2 0 1 0 0-2.4h-4.303l1.551-1.552Z"
                        clip-rule="evenodd"></path>
                </svg>

                <svg v-if="isPlaying" @click="togglePlay" style="margin-top: 4px;" t="1739596394231" class="icon"
                    viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="24623"
                    data-spm-anchor-id="a313x.search_index.0.i32.62833a81CA2DLm" width="24" height="24">
                    <path
                        d="M512 512m-428.218182 0a428.218182 428.218182 0 1 0 856.436364 0 428.218182 428.218182 0 1 0-856.436364 0Z"
                        fill="#ffffff" p-id="24624" data-spm-anchor-id="a313x.search_index.0.i29.62833a81CA2DLm"
                        class=""></path>
                    <path
                        d="M587.254691 336.244364c-18.283055 0-34.378473 16.039564-34.378473 34.471563v263.949964c0 18.432 16.095418 34.462255 34.378473 34.462254s34.378473-16.030255 34.378473-34.462254V370.715927c0-18.432-16.095418-34.462255-34.378473-34.462254z m-159.818473 0c-18.283055 0-34.378473 16.039564-34.378473 34.471563v263.949964c0 18.432 16.095418 34.462255 34.378473 34.462254s34.192291-16.030255 34.192291-34.462254V370.715927c0-18.432-15.909236-34.462255-34.192291-34.462254z"
                        fill="#060606" p-id="24625" data-spm-anchor-id="a313x.search_index.0.i28.62833a81CA2DLm"
                        class="selected"></path>
                    <path
                        d="M512 1019.345455c280.203636 0 507.345455-227.141818 507.345455-507.345455S792.203636 4.654545 512 4.654545 4.654545 231.796364 4.654545 512s227.141818 507.345455 507.345455 507.345455z m0-9.309091C236.944291 1010.036364 13.963636 787.055709 13.963636 512S236.944291 13.963636 512 13.963636s498.036364 222.980655 498.036364 498.036364-222.980655 498.036364-498.036364 498.036364z"
                        fill="#ffffff" p-id="24626" data-spm-anchor-id="a313x.search_index.0.i30.62833a81CA2DLm"
                        class=""></path>
                </svg>

                <svg v-else @click="togglePlay" style="margin-top: 6px;" t="1739595834879" class="icon"
                    viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="18745" width="20"
                    height="20">
                    <path
                        d="M907.2 251.2c49.6 73.6 80 163.2 80 260.8 0 256-208 464-464 464s-464-208-464-464 208-464 464-464c72 0 139.2 16 200 44.8"
                        fill="#ffffff" p-id="18746" data-spm-anchor-id="a313x.search_index.0.i16.62833a81CA2DLm"
                        class="selected"></path>
                    <path
                        d="M523.2 1008c-273.6 0-496-222.4-496-496s222.4-496 496-496c75.2 0 147.2 16 214.4 48 16 8 22.4 27.2 14.4 43.2-8 16-27.2 22.4-43.2 14.4C651.2 94.4 587.2 80 523.2 80c-238.4 0-432 193.6-432 432s193.6 432 432 432 432-193.6 432-432c0-86.4-25.6-171.2-73.6-241.6-9.6-14.4-6.4-35.2 8-44.8 14.4-9.6 35.2-6.4 44.8 8 56 81.6 84.8 179.2 84.8 278.4 0 273.6-222.4 496-496 496z"
                        fill="#ffffff" p-id="18747" data-spm-anchor-id="a313x.search_index.0.i15.62833a81CA2DLm"
                        class="selected"></path>
                    <path d="M400 310.4l363.2 208L400 728z" fill="#2c2c2c" p-id="18748"
                        data-spm-anchor-id="a313x.search_index.0.i17.62833a81CA2DLm" class=""></path>
                </svg>
                <svg @click="nextTrack" style="margin-top: 4px;" width="24" height="24" fill="currentColor"
                    viewBox="0 0 24 24">
                    <path fill-rule="evenodd"
                        d="M12 21.6a9.6 9.6 0 1 0 0-19.2 9.6 9.6 0 0 0 0 19.2Zm4.448-10.448-3.6-3.6a1.2 1.2 0 0 0-1.696 1.696l1.551 1.552H8.4a1.2 1.2 0 1 0 0 2.4h4.303l-1.551 1.552a1.2 1.2 0 1 0 1.696 1.696l3.6-3.6a1.2 1.2 0 0 0 0-1.696Z"
                        clip-rule="evenodd"></path>
                </svg>
                <div class="air"></div>

                <!-- 喜欢按钮 -->
                <svg @click="toggleLike" style="margin-top: 6px;margin-left: -50px;" width="24" height="20"
                    :fill="isLiked ? 'currentColor' : 'none'" stroke="currentColor" stroke-width="2"
                    viewBox="0 0 24 24">
                    <path
                        d="M3.343 7.778a4.5 4.5 0 0 1 7.339-1.46L12 7.636l1.318-1.318a4.5 4.5 0 1 1 6.364 6.364L12 20.364l-7.682-7.682a4.501 4.501 0 0 1-.975-4.904Z">
                    </path>
                </svg>

            </div>


            <!-- 进度条 -->
            <div class="time" @click="seek" @mousedown="startDrag">
                <div class="elapsed" :style="{ width: `${progress}%` }"></div>
            </div>
            <p class="timetext time_now">{{ formatTime(currentTime) }}</p>
            <p class="timetext time_full">{{ formatTime(duration) }}</p>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, watch } from 'vue';

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
const props = defineProps<{
    song: Song;
    audioPlayer: HTMLAudioElement;
}>();

const emit = defineEmits<{
    (event: 'previous'): void;
    (event: 'next'): void;
    (event: 'close'): void;
}>();

// 上一首和下一首的处理函数
const previousTrack = () => {
    emit('previous');
};

const nextTrack = () => {
    emit('next');
};

const closePlayer = () => {
  emit('close');
};

// 状态管理
const isPlaying = ref(false);
const isLiked = ref(false);
const showVolume = ref(true);
const volume = ref(80);
const progress = ref(0);
const currentTime = ref(0);
const duration = ref(0);

// 音频对象
const audio = new Audio(props.song.audioUrl);

// 格式化时间
const formatTime = (time: number) => {
    const minutes = Math.floor(time / 60);
    const seconds = Math.floor(time % 60);
    return `${minutes}:${seconds.toString().padStart(2, '0')}`;
};

// 控制函数
const togglePlay = () => {
    if (props.audioPlayer.paused) {
        props.audioPlayer.play();
    } else {
        props.audioPlayer.pause();
    }
    isPlaying.value = !props.audioPlayer.paused;
};


const previousVolume = ref(80);

// 音量控制函数
const toggleMute = () => {
    if (volume.value > 0) {
        // 当前有声音，执行禁音
        previousVolume.value = volume.value;
        volume.value = 0;
        props.audioPlayer.volume = 0;
        showVolume.value = false;
    } else {
        // 当前禁音，恢复声音
        volume.value = previousVolume.value;
        props.audioPlayer.volume = previousVolume.value / 100;
        showVolume.value = true;
    }
};

// 音量滑块调节函数
const adjustVolume = (event: MouseEvent) => {
    const slider = event.currentTarget as HTMLElement;
    const rect = slider.getBoundingClientRect();

    const updateVolume = (clientX: number) => {
        let x = clientX - rect.left;
        x = Math.max(0, Math.min(x, rect.width));  // 限制在滑块范围内
        const percent = (x / rect.width) * 100;
        volume.value = Math.round(percent);
        props.audioPlayer.volume = volume.value / 100;

        // 更新禁音状态
        showVolume.value = volume.value > 0;
        if (volume.value > 0) {
            previousVolume.value = volume.value;
        }
    };

    updateVolume(event.clientX);

    const onMouseMove = (e: MouseEvent) => {
        updateVolume(e.clientX);
    };

    const onMouseUp = () => {
        document.removeEventListener('mousemove', onMouseMove);
        document.removeEventListener('mouseup', onMouseUp);
    };

    document.addEventListener('mousemove', onMouseMove);
    document.addEventListener('mouseup', onMouseUp);
};

const seek = (event: MouseEvent) => {
    const slider = event.currentTarget as HTMLElement;
    const rect = slider.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const percentage = Math.max(0, Math.min(1, x / rect.width));
    const newTime = percentage * props.audioPlayer.duration;
    props.audioPlayer.currentTime = newTime;
    progress.value = percentage * 100;
};

// 添加进度条拖动功能
const startDrag = (event: MouseEvent) => {
    const slider = event.currentTarget as HTMLElement;

    const updateProgress = (clientX: number) => {
        const rect = slider.getBoundingClientRect();
        const x = clientX - rect.left;
        const percentage = Math.max(0, Math.min(1, x / rect.width));
        progress.value = percentage * 100;
        const newTime = percentage * props.audioPlayer.duration;
        props.audioPlayer.currentTime = newTime;
    };

    const onMouseMove = (e: MouseEvent) => {
        e.preventDefault();
        updateProgress(e.clientX);
    };

    const onMouseUp = () => {
        document.removeEventListener('mousemove', onMouseMove);
        document.removeEventListener('mouseup', onMouseUp);
    };

    document.addEventListener('mousemove', onMouseMove);
    document.addEventListener('mouseup', onMouseUp);
};

// 更新音频时间和进度
const updateTimeAndProgress = () => {
    if (props.audioPlayer) {
        currentTime.value = props.audioPlayer.currentTime;
        duration.value = props.audioPlayer.duration;
        progress.value = (props.audioPlayer.currentTime / props.audioPlayer.duration) * 100 || 0;
    }
};

// 更新播放状态的函数
const updatePlayingState = () => {
    isPlaying.value = !props.audioPlayer.paused;
};

// 生命周期钩子
onMounted(() => {
    if (props.audioPlayer) {
        // 添加现有的事件监听器
        props.audioPlayer.addEventListener('timeupdate', updateTimeAndProgress);
        props.audioPlayer.addEventListener('loadedmetadata', updateTimeAndProgress);

        // 添加播放状态事件监听器
        props.audioPlayer.addEventListener('play', updatePlayingState);
        props.audioPlayer.addEventListener('pause', updatePlayingState);

        // 初始化播放状态
        isPlaying.value = !props.audioPlayer.paused;
    }
});

onUnmounted(() => {
    if (props.audioPlayer) {
        // 移除现有的事件监听器
        props.audioPlayer.removeEventListener('timeupdate', updateTimeAndProgress);
        props.audioPlayer.removeEventListener('loadedmetadata', updateTimeAndProgress);

        // 移除播放状态事件监听器
        props.audioPlayer.removeEventListener('play', updatePlayingState);
        props.audioPlayer.removeEventListener('pause', updatePlayingState);
    }
});

// 监听 song prop 的变化
watch(() => props.song, () => {
    // 当歌曲改变时，更新播放状态
    isPlaying.value = !props.audioPlayer.paused;
});




</script>
