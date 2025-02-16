<style scoped>
.card {
    --rounded: 20px;
    --size: 360px;
    width: var(--size);
    height: calc(var(--size) - 10px);
    border-radius: var(--rounded);
    background: #fff;
    position: relative;
    overflow: hidden;
    cursor: pointer;
}

.card::before {
    --size: 100%;
    --blur: 20px;
    content: "";
    position: absolute;
    width: var(--size);
    height: var(--size);
    filter: blur(var(--blur));
    background-color: #faff99;
    background-image: radial-gradient(at 33% 82%, hsla(254, 71%, 69%, 1) 0px, transparent 50%),
        radial-gradient(at 28% 4%, hsla(289, 96%, 63%, 1) 0px, transparent 50%),
        radial-gradient(at 69% 49%, hsla(309, 91%, 71%, 1) 0px, transparent 50%),
        radial-gradient(at 94% 14%, hsla(232, 66%, 62%, 1) 0px, transparent 50%),
        radial-gradient(at 19% 93%, hsla(51, 98%, 74%, 1) 0px, transparent 50%),
        radial-gradient(at 15% 80%, hsla(194, 87%, 63%, 1) 0px, transparent 50%),
        radial-gradient(at 56% 52%, hsla(109, 71%, 61%, 1) 0px, transparent 50%);
}

.card::after {
    --size: 100%;
    content: "";
    position: absolute;
    width: var(--size);
    height: var(--size);
    background: rgba(255, 255, 255, 0.5);
}

.card__view {
    position: absolute;
    height: 55%;
    left: 0;
    top: 0;
    right: 0;
    margin: auto;
    z-index: 1;
    background-image: linear-gradient(to right bottom, #fff, #e2e2e2);
    background-repeat: no-repeat;
    background-size: cover;
}


.card:hover .preview__video {
    opacity: 100;
}

.preview__video {
    transition: opacity 300ms;
    opacity: 0;
}


.card__content {
    position: absolute;
    z-index: 2;
    width: 100%;
    height: 40%;
    left: 0;
    right: 0;
    bottom: 0;
    padding-left: 9px;
    padding-right: 9px;
    color: #000;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    top: 150px;
    font-family: Arial, Helvetica, sans-serif;
}

.channel__video__name {
    font-size: 0.8em;
    margin-bottom: 10px;
    max-height: 50%;
    overflow: hidden;
    text-overflow: ellipsis;
    font-weight: bold;
    color: #000;
}

.channel__data {
    display: flex;
    align-items: center;
    width: 100%;
    gap: 10px;
    margin-bottom: 8px;
}

/* .channel__img {
    --size-channel: 35px;
    --rounded: 50%;
    --duration: 300ms;
    min-width: var(--size-channel);
    min-height: var(--size-channel);
    border-radius: var(--rounded);
    transition: transform var(--duration);
    background-repeat: no-repeat;
    background-size: cover;
    background-color: #99cfff;
    background-image: radial-gradient(at 6% 14%, hsla(234, 90%, 72%, 1) 0px, transparent 50%),
        radial-gradient(at 9% 73%, hsla(298, 79%, 64%, 1) 0px, transparent 50%),
        radial-gradient(at 50% 38%, hsla(196, 72%, 75%, 1) 0px, transparent 50%),
        radial-gradient(at 61% 82%, hsla(288, 92%, 79%, 1) 0px, transparent 50%),
        radial-gradient(at 91% 67%, hsla(170, 86%, 66%, 1) 0px, transparent 50%),
        radial-gradient(at 83% 29%, hsla(289, 73%, 63%, 1) 0px, transparent 50%),
        radial-gradient(at 93% 34%, hsla(191, 88%, 75%, 1) 0px, transparent 50%);
} */



.channel__data__text {
    display: flex;
    flex-direction: column;
    gap: 5px;
    width: 100%;
}

.channel__subdata {
    display: flex;
    gap: 10px;
}

.channel__name {
    font-size: 1.2em;
    transition: color 400ms;
    margin-top: 40px;
}

.channel__views,
.channel__date {
    font-size: 0.6em;
}

.card__view__data {
    position: absolute;
    bottom: 0;
    width: 100%;
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    padding: 8px;
    box-sizing: border-box;
    pointer-events: none;
}

.card__lenght,
.card__play__icon,
.card__view__preview {
    background: rgba(0, 0, 0, 0.5);
    padding: 5px;
    border-radius: 6px;
    font-size: 0.6em;
    font-weight: bold;
    color: rgba(255, 255, 255, 0.7);
}

.card__play__icon {
    display: flex;
    justify-content: center;
    align-items: center;
    min-width: 30px;
}

.card__view__preview {
    opacity: 0;
    transform: translateY(-10px);
    transition: opacity 900ms, transform 900ms;
}

.card:hover .card__view__preview {
    opacity: 100;
    transform: translateY(0px);
}

.card:nth-child(2n)::before {
    background-color: #ffd399;
    background-image: radial-gradient(at 15% 65%, hsla(249, 84%, 60%, 1) 0px, transparent 50%),
        radial-gradient(at 38% 24%, hsla(197, 80%, 66%, 1) 0px, transparent 50%),
        radial-gradient(at 65% 93%, hsla(228, 83%, 60%, 1) 0px, transparent 50%),
        radial-gradient(at 16% 58%, hsla(314, 83%, 72%, 1) 0px, transparent 50%),
        radial-gradient(at 77% 57%, hsla(298, 90%, 78%, 1) 0px, transparent 50%),
        radial-gradient(at 76% 95%, hsla(178, 64%, 76%, 1) 0px, transparent 50%),
        radial-gradient(at 15% 61%, hsla(248, 75%, 64%, 1) 0px, transparent 50%);
}

.card:nth-child(3n)::before {
    background-color: #99b3ff;
    background-image: radial-gradient(at 93% 72%, hsla(293, 71%, 70%, 1) 0px, transparent 50%),
        radial-gradient(at 28% 37%, hsla(252, 81%, 71%, 1) 0px, transparent 50%),
        radial-gradient(at 25% 59%, hsla(158, 67%, 62%, 1) 0px, transparent 50%),
        radial-gradient(at 48% 16%, hsla(197, 69%, 63%, 1) 0px, transparent 50%),
        radial-gradient(at 70% 56%, hsla(171, 69%, 70%, 1) 0px, transparent 50%),
        radial-gradient(at 7% 33%, hsla(65, 65%, 79%, 1) 0px, transparent 50%),
        radial-gradient(at 89% 22%, hsla(286, 64%, 72%, 1) 0px, transparent 50%);
}

.card:nth-child(4n)::before {
    background-color: #e2ff99;
    background-image: radial-gradient(at 22% 80%, hsla(265, 77%, 67%, 1) 0px, transparent 50%),
        radial-gradient(at 91% 77%, hsla(292, 60%, 78%, 1) 0px, transparent 50%),
        radial-gradient(at 66% 7%, hsla(46, 95%, 76%, 1) 0px, transparent 50%),
        radial-gradient(at 16% 32%, hsla(275, 90%, 75%, 1) 0px, transparent 50%),
        radial-gradient(at 26% 23%, hsla(283, 60%, 69%, 1) 0px, transparent 50%),
        radial-gradient(at 2% 64%, hsla(208, 62%, 62%, 1) 0px, transparent 50%),
        radial-gradient(at 62% 77%, hsla(112, 88%, 75%, 1) 0px, transparent 50%);
}

.card:nth-child(5n)::before {
    background-color: #99d1ff;
    background-image: radial-gradient(at 21% 94%, hsla(270, 95%, 76%, 1) 0px, transparent 50%),
        radial-gradient(at 56% 5%, hsla(252, 65%, 79%, 1) 0px, transparent 50%),
        radial-gradient(at 67% 94%, hsla(194, 81%, 67%, 1) 0px, transparent 50%),
        radial-gradient(at 15% 72%, hsla(304, 97%, 77%, 1) 0px, transparent 50%),
        radial-gradient(at 63% 56%, hsla(38, 98%, 63%, 1) 0px, transparent 50%),
        radial-gradient(at 41% 67%, hsla(321, 61%, 77%, 1) 0px, transparent 50%),
        radial-gradient(at 68% 68%, hsla(263, 67%, 69%, 1) 0px, transparent 50%);
}

.rating {
    position: absolute;
    top: -120px;
    right: 10px;
    background: rgba(0, 0, 0, 0.7);
    color: #fff;
    padding: 5px 10px;
    border-radius: 5px;
    font-size: 1.2em;
    font-weight: bold;
    z-index: 3;
    font-family: Arial, Helvetica, sans-serif;
}

.card__view {
    position: absolute;
    height: 55%;
    /* 图片区域高度 */
    width: 100%;
    /* 图片区域宽度 */
    background-image: url(https://www.alexcat.it.com/minio-api/public-errs/MoveTest.jpg);
    background-size: cover;
    /* 图片覆盖整个区域，可能裁剪 */
    background-position: center;
    /* 图片居中显示 */
    background-repeat: no-repeat;
    /* 防止图片重复 */
    /* filter: blur(4px) brightness(0.9); */
}

.card__view:hover {
    /* 一秒之后 不再模糊 */
    transition: filter 1s;
    filter: blur(0px) brightness(1);
}

.rating-high {
    color: #ffd700; /* 金色 */
}
.rating-good {
    color: #52c41a; /* 绿色 */
}
.rating-medium {
    color: #fa8c16; /* 橙色 */
}
.rating-low {
    color: #f5222d; /* 红色 */
}
.recommend-tag {
    position: absolute;
    top: 10px;
    left: 10px;
    background: #ff4d4f;
    color: white;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 0.8em;
    z-index: 3;
}
</style>

<template>
    <div class="card">
        <div class="card__view">
            <!-- 推荐标签 -->
            <div v-if="isRecommended" class="recommend-tag">编辑推荐</div>
            
            <div class="card__view__data">
                <p class="card__view__preview">预览</p>
                <p class="card__play__icon" @click.stop="toggleVideo">
                    <!-- 播放/暂停图标 -->
                    <svg v-if="!isPlaying" width="8" height="8" viewBox="-0.5 0 7 7">
                        <path fill="#EAECEE" d="M296.494737,3608.57322 L292.500752,3606.14219 C291.83208,3605.73542 291,3606.25002 291,3607.06891 L291,3611.93095 C291,3612.7509 291.83208,3613.26444 292.500752,3612.85767 L296.494737,3610.42771 C297.168421,3610.01774 297.168421,3608.98319 296.494737,3608.57322"/>
                    </svg>
                    <svg v-else t="1739637325113" viewBox="0 0 1024 1024" width="14" height="14">
                        <path d="M512 64C264.64 64 64 264.64 64 512c0 247.424 200.64 448 448 448 247.424 0 448-200.576 448-448C960 264.64 759.424 64 512 64zM448 635.584c0 23.552-18.432 42.688-41.216 42.624-22.656 0.064-41.088-19.072-41.088-42.56L365.696 383.04c0-23.68 18.432-42.688 41.152-42.688C429.568 340.224 448 359.488 448 382.976L448 635.584zM658.24 635.584c0 23.552-18.432 42.688-41.216 42.624C594.368 678.272 576 659.136 576 635.648L576 383.04c0-23.68 18.432-42.688 41.152-42.688 22.656-0.128 41.152 19.136 41.152 42.624L658.304 635.584z" fill="#FFF"/>
                    </svg>
                </p>
                <!-- 动态时长显示 -->
                <p class="card__lenght">{{ formatDuration(duration) }}</p>
                <!-- 动态评分颜色 -->
                <div class="rating" :class="ratingClass">{{ rating.toFixed(1) }}</div>
            </div>
        </div>
        <div class="card__content">
            <p class="channel__video__name">{{ title }}</p>
            <div class="channel__data">
                <div class="channel__img">
                    <img :src="poster" :alt="title" style="width:77px;height:108px;margin-top:40px;">
                </div>
                <div class="channel__data__text">
                    <p class="channel__name">{{ director }}</p>
                    <div class="channel__subdata">
                        <p class="channel__views">{{ formatViews(views) }}次观看</p>
                        <p class="channel__date">{{ releaseDate }}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, computed } from "vue";

const props = defineProps({
    title: { type: String, required: true },
    rating: { type: Number, default: 0 },
    releaseDate: { type: String, required: true },
    views: { type: Number, default: 0 },
    duration: { type: Number, default: 0 }, // 单位：秒
    poster: { type: String, required: true },
    videoUrl: { type: String },
    director: { type: String, required: true },
    isRecommended: { type: Boolean, default: false }
});

const isPlaying = ref(false);

// 评分颜色逻辑
const ratingClass = computed(() => {
    if (props.rating >= 9) return 'rating-high';
    if (props.rating >= 8) return 'rating-good';
    if (props.rating >= 6) return 'rating-medium';
    return 'rating-low';
});

// 播放控制
const toggleVideo = () => {
    isPlaying.value = !isPlaying.value;
    // 这里可以添加实际视频控制逻辑
    if (isPlaying.value) {
        console.log('开始播放:', props.videoUrl);
    } else {
        console.log('暂停播放');
    }
};

// 格式化观看次数
const formatViews = (views: number) => {
    if (views >= 1e8) return (views / 1e8).toFixed(1) + '亿';
    if (views >= 1e4) return (views / 1e4).toFixed(1) + '万';
    return views;
};

// 格式化时长
const formatDuration = (seconds: number) => {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
};
</script>
