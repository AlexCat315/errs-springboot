<style scoped>
/* In this section you start by assigning a shape to the letter and an assigned size. */
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

/* With the before property assigns the gradient that will be used as background for the chart. */
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

/* Cover the chart with a light white tone, this helps the gradient colors to be less aggressive. */
.card::after {
    --size: 100%;
    content: "";
    position: absolute;
    width: var(--size);
    height: var(--size);
    background: rgba(255, 255, 255, 0.5);
}

/*
  In this section you define where the video cover will be displayed.
  Modify the background-image with the address of the cover to use. */
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

/* When hovering over the element you can see the video preview. */
/* Place in the HTML an "iframe" tag with the address of the youtube video to show as preview.
     The iframe tag must be a child of the div with the class "card__view".
     Also place a class with name "preview__video". */
.card:hover .preview__video {
    opacity: 100;
}

.preview__video {
    transition: opacity 300ms;
    opacity: 0;
}


/* In this section you define the size and position of the section that will contain the video content. */
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

/* In this section you assign the image of the channel that owns the video, modify the parameter "background-image" by the address of the channel cover. */
.channel__img {
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
}

.channel__img:hover {
    transform: scale(1.08);
}

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
    font-size: 0.8em;
    transition: color 400ms;
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

/* List of other colors you can use */
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
    filter: blur(4px) brightness(0.9);
}

.card__view:hover {
    /* 一秒之后 不再模糊 */
    transition: filter 1s;
    filter: blur(0px) brightness(1);
}
</style>

<template>
    <div class="card">
        <div :style="{

        }" class="card__view">
            <div class="card__view__data">
                <p class="card__view__preview">预览</p>
                <!-- 播放icon -->
                <p class="card__play__icon">
                    <svg @click="playVideo" v-if="!isPlayStatus" width="8px" height="8px" viewBox="-0.5 0 7 7"
                        version="1.1">
                        <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                            <g id="Dribbble-Light-Preview" transform="translate(-347.000000, -3766.000000)"
                                fill="#000000">
                                <g id="icons" transform="translate(56.000000, 160.000000)">
                                    <path fill="#EAECEE"
                                        d="M296.494737,3608.57322 L292.500752,3606.14219 C291.83208,3605.73542 291,3606.25002 291,3607.06891 L291,3611.93095 C291,3612.7509 291.83208,3613.26444 292.500752,3612.85767 L296.494737,3610.42771 C297.168421,3610.01774 297.168421,3608.98319 296.494737,3608.57322"
                                        id="play-[#1003]"></path>
                                </g>
                            </g>
                        </g>
                    </svg>
                    <svg @click="pauseVideo" v-else t="1739637325113" class="icon" viewBox="0 0 1024 1024" version="1.1"
                        xmlns="http://www.w3.org/2000/svg" p-id="5842" width="14px" height="14px">
                        <path
                            d="M512 64C264.64 64 64 264.64 64 512c0 247.424 200.64 448 448 448 247.424 0 448-200.576 448-448C960 264.64 759.424 64 512 64zM448 635.584c0 23.552-18.432 42.688-41.216 42.624-22.656 0.064-41.088-19.072-41.088-42.56L365.696 383.04c0-23.68 18.432-42.688 41.152-42.688C429.568 340.224 448 359.488 448 382.976L448 635.584zM658.24 635.584c0 23.552-18.432 42.688-41.216 42.624C594.368 678.272 576 659.136 576 635.648L576 383.04c0-23.68 18.432-42.688 41.152-42.688 22.656-0.128 41.152 19.136 41.152 42.624L658.304 635.584z"
                            fill="#FFF" p-id="5843"></path>
                    </svg>
                </p>
                <!-- 总时长 -->
                <p class="card__lenght">10:19</p>
                <!-- 评分 -->
                <div class="rating">8.5</div>
            </div>
        </div>
        <div class="card__content">
            <p class="channel__video__name"></p>
            <div class="channel__data">
                <div class="channel__img "></div>
                <div class="channel__data__text">
                    <p class="channel__name">SmarterEveryDay</p>
                    <div class="channel__subdata">
                        <p class="channel__views">519.7K Views</p>
                        <p class="channel__date">3 months ago</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from "vue"

const isPlayStatus = ref(false);

const playVideo = () => {
  isPlayStatus.value = true;
}

const pauseVideo = () => {
  isPlayStatus.value = false
}
</script>
