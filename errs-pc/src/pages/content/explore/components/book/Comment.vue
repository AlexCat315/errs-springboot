<style scoped>
.task {
    position: relative;
    color: #2e2e2f;
    cursor: pointer;
    background-color: #fff;
    padding: 1rem;
    border-radius: 8px;
    box-shadow: rgba(99, 99, 99, 0.1) 0px 2px 8px 0px;
    margin-bottom: 1rem;
    border: 3px dashed transparent;
    max-width: 350px;
}

.task:hover {
    box-shadow: rgba(99, 99, 99, 0.3) 0px 2px 8px 0px;
    border-color: rgba(162, 179, 207, 0.2) !important;
}

.task p {
    font-size: 15px;
    margin: 1.2rem 0;
}

.tag {
    border-radius: 100px;
    padding: 4px 13px;
    font-size: 12px;
    color: #ffffff;
    background-color: #1389eb;
}

.tags {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.options {
    background: transparent;
    border: 0;
    color: #c4cad3;
    font-size: 17px;
}

.options svg {
    fill: #9fa4aa;
    width: 20px;
}

.stats {
    position: relative;
    width: 100%;
    color: #9fa4aa;
    font-size: 12px;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.stats div {
    margin-right: 1rem;
    height: 20px;
    display: flex;
    align-items: center;
    cursor: pointer;
}

.stats svg {
    margin-right: 5px;
    height: 100%;
    stroke: #9fa4aa;
}

.viewer span {
    height: 30px;
    width: 30px;
    background-color: rgb(28, 117, 219);
    margin-right: -10px;
    border-radius: 50%;
    border: 1px solid #fff;
    display: grid;
    align-items: center;
    text-align: center;
    font-weight: bold;
    color: #fff;
    padding: 2px;
}

.viewer span svg {
    stroke: #fff;
}
</style>

<template>
    <div :style="{
        color: globalTheme === 'dark' ? '#898989' : '#5f6064',
        background: globalTheme === 'dark' ? '#3d3d44' : '#fff',
    }" class="task" draggable="true">
        <div class="tags">
            <span :style="{background: colorsRandom() }" class="tag">编辑精选</span>
            <button class="options">
                <svg xml:space="preserve" viewBox="0 0 41.915 41.916" xmlns:xlink="http://www.w3.org/1999/xlink"
                    xmlns="http://www.w3.org/2000/svg" id="Capa_1" version="1.1" fill="#000000">
                    <g stroke-width="0" id="SVGRepo_bgCarrier"></g>
                    <g stroke-linejoin="round" stroke-linecap="round" id="SVGRepo_tracerCarrier"></g>
                    <g id="SVGRepo_iconCarrier">
                        <g>
                            <g>
                                <path
                                    d="M11.214,20.956c0,3.091-2.509,5.589-5.607,5.589C2.51,26.544,0,24.046,0,20.956c0-3.082,2.511-5.585,5.607-5.585 C8.705,15.371,11.214,17.874,11.214,20.956z">
                                </path>
                                <path
                                    d="M26.564,20.956c0,3.091-2.509,5.589-5.606,5.589c-3.097,0-5.607-2.498-5.607-5.589c0-3.082,2.511-5.585,5.607-5.585 C24.056,15.371,26.564,17.874,26.564,20.956z">
                                </path>
                                <path
                                    d="M41.915,20.956c0,3.091-2.509,5.589-5.607,5.589c-3.097,0-5.606-2.498-5.606-5.589c0-3.082,2.511-5.585,5.606-5.585 C39.406,15.371,41.915,17.874,41.915,20.956z">
                                </path>
                            </g>
                        </g>
                    </g>
                </svg>
            </button>
        </div>
        <p>
            {{ getRandomCommentText() }}
        </p>
        <div class="stats">
            <div>
                <div>
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <g stroke-width="0" id="SVGRepo_bgCarrier"></g>
                        <g stroke-linejoin="round" stroke-linecap="round" id="SVGRepo_tracerCarrier"></g>
                        <g id="SVGRepo_iconCarrier">
                            <path stroke-linecap="round" stroke-width="2" d="M12 8V12L15 15"></path>
                            <circle stroke-width="2" r="9" cy="12" cx="12"></circle>
                        </g>
                    </svg>{{ getRandomNumber() }} 天前
                </div>
                <div>
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <g stroke-width="0" id="SVGRepo_bgCarrier"></g>
                        <g stroke-linejoin="round" stroke-linecap="round" id="SVGRepo_tracerCarrier"></g>
                        <g id="SVGRepo_iconCarrier">
                            <path stroke-linejoin="round" stroke-linecap="round" stroke-width="1.5"
                                d="M16 10H16.01M12 10H12.01M8 10H8.01M3 10C3 4.64706 5.11765 3 12 3C18.8824 3 21 4.64706 21 10C21 15.3529 18.8824 17 12 17C11.6592 17 11.3301 16.996 11.0124 16.9876L7 21V16.4939C4.0328 15.6692 3 13.7383 3 10Z">
                            </path>
                        </g>
                    </svg>{{getRandomNumber() * 7}}
                </div>
                <div>
                    <svg fill="#000000" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns="http://www.w3.org/2000/svg"
                        version="1.1" viewBox="-2.5 0 32 32">
                        <g stroke-width="0" id="SVGRepo_bgCarrier"></g>
                        <g stroke-linejoin="round" stroke-linecap="round" id="SVGRepo_tracerCarrier"></g>
                        <g id="SVGRepo_iconCarrier">
                            <g id="icomoon-ignore"></g>
                            <path fill="#000000"
                                d="M0 10.284l0.505 0.36c0.089 0.064 0.92 0.621 2.604 0.621 0.27 0 0.55-0.015 0.836-0.044 3.752 4.346 6.411 7.472 7.060 8.299-1.227 2.735-1.42 5.808-0.537 8.686l0.256 0.834 7.63-7.631 8.309 8.309 0.742-0.742-8.309-8.309 7.631-7.631-0.834-0.255c-2.829-0.868-5.986-0.672-8.686 0.537-0.825-0.648-3.942-3.3-8.28-7.044 0.11-0.669 0.23-2.183-0.575-3.441l-0.352-0.549-8.001 8.001zM1.729 10.039l6.032-6.033c0.385 1.122 0.090 2.319 0.086 2.334l-0.080 0.314 0.245 0.214c7.409 6.398 8.631 7.39 8.992 7.546l-0.002 0.006 0.195 0.058 0.185-0.087c2.257-1.079 4.903-1.378 7.343-0.836l-13.482 13.481c-0.55-2.47-0.262-5.045 0.837-7.342l0.104-0.218-0.098-0.221-0.031 0.013c-0.322-0.632-1.831-2.38-7.498-8.944l-0.185-0.215-0.282 0.038c-0.338 0.045-0.668 0.069-0.981 0.069-0.595 0-1.053-0.083-1.38-0.176z">
                            </path>
                        </g>
                    </svg>{{getRandomNumber() * 3}}
                </div>
            </div>
            <div class="viewer">
                <span><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <g stroke-width="0" id="SVGRepo_bgCarrier"></g>
                        <g stroke-linejoin="round" stroke-linecap="round" id="SVGRepo_tracerCarrier"></g>
                        <g id="SVGRepo_iconCarrier">
                            <path stroke-width="2" stroke="#ffffff"
                                d="M17 8C17 10.7614 14.7614 13 12 13C9.23858 13 7 10.7614 7 8C7 5.23858 9.23858 3 12 3C14.7614 3 17 5.23858 17 8Z">
                            </path>
                            <path stroke-linecap="round" stroke-width="2" stroke="#ffffff"
                                d="M3 21C3.95728 17.9237 6.41998 17 12 17C17.58 17 20.0427 17.9237 21 21"></path>
                        </g>
                    </svg></span>
                <span><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <g stroke-width="0" id="SVGRepo_bgCarrier"></g>
                        <g stroke-linejoin="round" stroke-linecap="round" id="SVGRepo_tracerCarrier"></g>
                        <g id="SVGRepo_iconCarrier">
                            <path stroke-width="2" stroke="#ffffff"
                                d="M17 8C17 10.7614 14.7614 13 12 13C9.23858 13 7 10.7614 7 8C7 5.23858 9.23858 3 12 3C14.7614 3 17 5.23858 17 8Z">
                            </path>
                            <path stroke-linecap="round" stroke-width="2" stroke="#ffffff"
                                d="M3 21C3.95728 17.9237 6.41998 17 12 17C17.58 17 20.0427 17.9237 21 21"></path>
                        </g>
                    </svg></span>
                <span><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <g stroke-width="0" id="SVGRepo_bgCarrier"></g>
                        <g stroke-linejoin="round" stroke-linecap="round" id="SVGRepo_tracerCarrier"></g>
                        <g id="SVGRepo_iconCarrier">
                            <path stroke-width="2" stroke="#ffffff"
                                d="M17 8C17 10.7614 14.7614 13 12 13C9.23858 13 7 10.7614 7 8C7 5.23858 9.23858 3 12 3C14.7614 3 17 5.23858 17 8Z">
                            </path>
                            <path stroke-linecap="round" stroke-width="2" stroke="#ffffff"
                                d="M3 21C3.95728 17.9237 6.41998 17 12 17C17.58 17 20.0427 17.9237 21 21"></path>
                        </g>
                    </svg></span>
                <span>+{{getRandomNumber() * 5 + 3}}</span>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { inject } from "vue";

const globalTheme = inject<string>("globalTheme");

const bookComments = [
    {
        id: 1,
        text: "情节构思巧妙，人物塑造生动。作者对人性的细腻刻画和深刻洞察让人印象深刻，读来令人回味无穷。"
    },
    {
        id: 2,
        text: "文笔优美流畅，意境深远。故事情节跌宕起伏，能让读者完全沉浸其中，不愿释卷。"
    },
    {
        id: 3,
        text: "这部小说展现了非凡的想象力和艺术感染力，字里行间蕴含着深刻的人生哲理，值得反复品读。"
    },
    {
        id: 4,
        text: "人物形象鲜活，性格特征鲜明，剧情发展引人入胜。作者对社会现实的思考和批判令人深思。"
    },
    {
        id: 5,
        text: "叙事手法独特，故事层次丰富。作品既有宏大的历史视野，又不失对个人命运的细致描绘。"
    },
    {
        id: 6,
        text: "情节环环相扣，悬念设置巧妙。作者在故事中融入的思想内涵和情感表达令人感同身受。"
    },
    {
        id: 7,
        text: "这是一部令人难忘的杰作，既有跌宕起伏的故事情节，又包含深邃的人生思考和哲理。"
    },
    {
        id: 8,
        text: "角色塑造入木三分，情节发展引人入胜。作品中蕴含的人性观察和社会洞察给人深刻启发。"
    },
    {
        id: 9,
        text: "叙事节奏把控得当，故事结构完整精妙。作者对人物心理的刻画细腻传神，令人印象深刻。"
    },
    {
        id: 10,
        text: "文学价值和艺术成就都很高的一部作品，既能引发读者的情感共鸣，又能带来深刻的思考。"
    }
]
const colors = [
    "#ec8236",
    "#f56c6c",
    "#67c23a",
    "#e6a23c",
    "#409eff",
    "#c239e6",
    "#5941d3",
];
const colorsRandom = () => colors[Math.floor(Math.random() * colors.length)];
// 随机返回，只返回text
function getRandomCommentText() {
    const randomIndex = Math.floor(Math.random() * bookComments.length);
    return bookComments[randomIndex].text;
}
// 随机返回1-12
const getRandomNumber = () => Math.floor(Math.random() * 12) + 1;

</script>
