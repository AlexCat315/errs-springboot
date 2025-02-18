<script lang="ts" setup>
import { onMounted, ref, inject, Ref,watch } from "vue";
import { get_move_search } from "../../../../../net/movie/get"; // 修改为电影接口
import Like from "./Like.vue";

// 定义 Movie 类型
interface Movie {
    id: number;
    name: string;
    rating: number;
    director: string;
    actor: string;
    year: string;
    language: string;
    summary: string;
    types: string[];
    cover: string;
    users: number;
}

const moviesList = ref<Movie[]>([]);

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

onMounted(() => {
    fetchMovies();
});



const currentPage = ref(1);
const scrollContainer = ref<HTMLElement | null>(null);
const globalSearch = inject("globalSearch") as Ref<string>;
if (!globalSearch) {
    throw new Error("globalSearch is not provided");
}

const fetchMovies = (page = 1, size = 10) => {
  if (globalSearch.value === '') {
    return;
  }
    get_move_search(
        page,
        size,
        globalSearch.value,
        (data: any) => {
            console.log(data);
            const newMovies = data.data.map((movie: any) => ({
                ...movie,
                users: formatNumber(movie.users),
                rating: parseFloat(movie.rating.toFixed(1)),
                year: new Date(movie.year).toLocaleDateString()
            }));

            if (currentPage.value === 1) {
                moviesList.value = newMovies;
            } else {
                const uniqueMovies = [...moviesList.value, ...newMovies].filter(
                    (movie, index, self) =>
                        self.findIndex((m) => m.id === movie.id) === index
                );
                moviesList.value = uniqueMovies;
            }
        },
        () => { }
    );
};
watch(() => globalSearch.value, () => {
  console.log('globalSearch changed', globalSearch.value);
    currentPage.value = 1;
    moviesList.value = [];
    if (globalSearch.value !== '') {
        fetchMovies();
    }
});

const handleScroll = () => {
    const container = scrollContainer.value;
    if (!container) return;

    const { scrollTop, scrollHeight, clientHeight } = container;
    const isBottom = scrollTop + clientHeight >= scrollHeight - 5;

    if (isBottom) {
        currentPage.value += 1;
        fetchMovies(currentPage.value);
    }
};

const gameID = ref();
const emit = defineEmits(['update:modelValue']);

const showRatingCard = (movieId: number) => {
    emit('update:modelValue', movieId);
};
</script>

<template>
    <div class="container">
        <div ref="scrollContainer" @scroll="handleScroll" class="gard">
            <div v-for="(item, index) in moviesList" :key="item.id" class="card">
                <div style="display: flex">
                    <p class="rank-index">
                        {{ index + 1 }}
                    </p>
                    <img alt="cover" class="movies-img" :src="item.cover" />
                    <div style="width: 90%">
                        <!-- 标题和基本信息 -->
                        <div class="text-with-tags app-title">
                            <div style="display: flex; align-items: center">
                                <span class="text text-default--size">{{ item.name }}</span>
                                <span style="
                                        margin-left: 15px;
                                        font-size: 13px;
                                        color: #666;
                                    ">{{ item.year }}</span>
                            </div>

                        </div>

                        <!-- 评分和主创 -->
                        <div style="margin-top: 5px" class="flex-center--y">
                            <div class="tap-rating tap-rating-medium">
                                <svg class="svg-icon" viewBox="0 0 25 24">
                                    <path
                                        d="M16.278 8.22c-.938-.019-1.407-.028-1.811-.186a2.43 2.43 0 0 1-.931-.648c-.289-.323-.46-.76-.804-1.633l-.346-.88c-.42-1.067-.629-1.6-.944-1.774a.997.997 0 0 0-.89-.036c-.328.148-.58.663-1.083 1.693L8.414 6.914c-.319.652-.478.978-.71 1.234a2.431 2.431 0 0 1-.728.548c-.31.152-.668.215-1.383.341l-1.95.343c-1.15.203-1.724.304-1.957.582a.997.997 0 0 0-.206.87c.083.353.551.702 1.487 1.398l.96.715c.796.592 1.194.889 1.436 1.274.213.34.34.728.367 1.129.03.454-.117.927-.412 1.875l-.104.332c-.362 1.161-.542 1.742-.397 2.08a.997.997 0 0 0 .699.579c.36.08.896-.205 1.97-.776l1.228-.653c.715-.381 1.073-.572 1.449-.639.333-.06.674-.049 1.002.03.371.09.717.303 1.408.726l2.07 1.268c1.031.632 1.547.948 1.91.89a.997.997 0 0 0 .733-.532c.167-.329.024-.917-.26-2.093l-.457-1.888c-.212-.877-.318-1.315-.275-1.732.039-.368.161-.723.358-1.037.222-.356.576-.636 1.282-1.196l1.855-1.469c1.072-.849 1.607-1.273 1.67-1.652a.996.996 0 0 0-.3-.89c-.28-.263-.962-.276-2.329-.303l-2.552-.049Z">
                                    </path>
                                </svg>
                                <div class="tap-rating__number rate-number-font">
                                    {{ item.rating }}
                                </div>
                            </div>

                            <div style="margin-left: 20px; color: #666;display: flex;">
                                <span style="font-size: 11px;min-width: 100px;">导演：{{ item.director }}</span>
                                <span class="actor" style="margin-top: 3px;">主演：{{ item.actor }}</span>
                            </div>
                        </div>

                        <!-- 其他信息 -->
                        <div style="margin-top: 1px; color: #888;font-size: 11px;margin-left: 15px;">
                            <span>语言：{{ item.language }}</span>
                            <span style="margin-left: 20px">评价人数：{{ item.users }}</span>
                        </div>

                        <!-- 简介 -->
                        <div class="description-container">
                            <p class="description">
                                {{ item.summary }}
                            </p>
                        </div>
                        <div style="display: flex; margin-top: -5px;margin-left: 10px;">
                            <button v-for="tag in item.types" :key="tag" :style="{ backgroundColor: colorsRandom() }"
                                class="tag-button">
                                {{ tag }}
                            </button>
                        </div>
                    </div>

                    <div style="display: flex">
                        <Like @click="showRatingCard(item.id)" style="margin-right: 10px; margin-top: 10px" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.gard::-webkit-scrollbar {
    width: 0;
    /* 隐藏滚动条 */
}

.gard::-webkit-scrollbar-thumb {
    background: transparent;
    /* 背景透明，实际上隐藏了滑块 */
}

.section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

}

.gard {
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 1rem;
    margin-top: -20px;
    transform-origin: center;
    box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
    /* 添加阴影效果 */
    margin-bottom: 80px;
    z-index: 1;
    background: #fff;
    border-radius: 20px;
    max-width: 100%;
    overflow-y: auto;
    height: 100vh;
    scrollbar-width: none;
    /* 隐藏滚动条 */
    margin-top: 10px;
    margin-bottom: 150px;
}

.card {
    height: 150px;
    cursor: pointer;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    width: 100%;
}

.rank-index {
    font-family:
        rate-number-font,
        -apple-system,
        BlinkMacSystemFont,
        Segoe UI,
        Helvetica,
        Arial,
        PingFang SC,
        Noto Sans,
        Roboto,
        Microsoft Yahei,
        sans-serif,
        Apple Color Emoji,
        Segoe UI Emoji;
    -webkit-font-smoothing: antialiased;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    text-align: center;
    font-size: 20px;
    font-weight: 900;
    width: 40px;
    flex-shrink: 0;
    padding-right: 4px;
    padding-top: 30px;
}

.games-img {
    object-fit: cover;
    display: block;
    min-width: 100px;
    max-width: 100px;
    height: 100px;
    font-size: 0;
    box-shadow: 0;
    border-radius: 22.78%;
    margin-top: 25px;
}

.text-default--size {
    font-size: 14px;
    line-height: 18px;
}

.text {
    margin-right: 4px;
    font-weight: 700;
}

.text-with-tags {
    position: relative;
    display: block;
}

.app-title {
    margin-top: 5px;
    margin-left: 13px;
    font-family:
        -apple-system,
        BlinkMacSystemFont,
        Segoe UI,
        Helvetica,
        Arial,
        PingFang SC,
        Noto Sans,
        Roboto,
        Microsoft Yahei,
        sans-serif,
        Apple Color Emoji,
        Segoe UI Emoji;
}

.svg-icon {
    fill: currentColor;
    width: 1em;
    height: 1em;
    vertical-align: top;
    margin-left: 14px;
}

.flex-center,
.flex-center--y {
    display: -webkit-box;
    display: flex;
    -webkit-box-align: center;
    align-items: center;
    margin-top: 6px;
}

.tap-rating {
    display: -webkit-box;
    display: flex;
    -webkit-box-orient: horizontal;
    -webkit-box-direction: normal;
    flex-direction: row;
    -webkit-box-pack: start;
    justify-content: flex-start;
    -webkit-box-align: center;
    align-items: center;
    color: rgb(0, 205, 186);
}

a,
button,
div,
input {
    outline: none;
    -webkit-tap-highlight-color: transparent;
}

.rate-number-font {
    font-family:
        rate-number-font,
        -apple-system,
        BlinkMacSystemFont,
        Segoe UI,
        Helvetica,
        Arial,
        PingFang SC,
        Noto Sans,
        Roboto,
        Microsoft Yahei,
        sans-serif,
        Apple Color Emoji,
        Segoe UI Emoji;
}

.tap-label-tag-group {
    flex-wrap: nowrap;
    max-height: calc(18px);
    max-width: 100%;
}

.tap-ellipsis {
    text-overflow: ellipsis;
    white-space: nowrap;
}

.tap-label-tag {
    position: relative;
    color: #93999e;
    display: inline-block;
    font-size: 12px;
    text-decoration: none;
    margin-top: -15px;
    font-family:
        system-ui,
        -apple-system,
        BlinkMacSystemFont,
        "Segoe UI",
        Roboto,
        Oxygen,
        Ubuntu,
        Cantarell,
        "Open Sans",
        "Helvetica Neue",
        sans-serif;
}

.game-cell__tags a:not(:last-child)::after {
    content: "·";
    margin: 0 0.5em;
    color: #999;
    display: inline-block;
}

.description {
    color: #666;
    font-size: 0.95rem;
    display: -webkit-box;
    /* 必须，开启弹性盒子布局 */
    -webkit-line-clamp: 2;
    /* 设置显示的行数为 6 行 */
    line-clamp: 2;
    -webkit-box-orient: vertical;
    /* 必须，设置盒子方向为垂直 */
    overflow: hidden;
    /* 必须，隐藏多余的内容 */
    text-overflow: ellipsis;
    /* 添加省略号 */
    max-height: calc(1.4em * 4);
    /* 设置高度为 6 行高度 */
    font-family: wenquanyimihei;
    font-size: 11px;
    margin-top: 5px !important;
    margin-left: 15px;
    margin-right: 40px;
}

.actor {
    color: #666;
    font-size: 1.2rem;
    display: -webkit-box;
    /* 必须，开启弹性盒子布局 */
    -webkit-line-clamp: 1;
    line-clamp: 1;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
    max-height: calc(1.4em * 4);
    font-family: wenquanyimihei;
    font-size: 11px;
    margin-top: 1px;
    margin-left: 15px;
    margin-right: 40px;
}

/* Loading 遮罩层 */
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
    z-index: 2;
    /* 确保遮罩层在最上层 */
}

.card {
    height: 180px;
    /* 增加高度适应更多信息 */
    padding: 15px 0;
}

.movies-img {
    width: 120px;
    /* 调整图片尺寸 */
    height: 160px;
    border-radius: 8px;
    margin-right: 20px;
}

.text-default--size {
    font-size: 18px;
    font-weight: 600;
}

.tag-button {
    margin: 2px 4px;
    padding: 4px 12px;
    border-radius: 15px;
    font-size: 12px;
    color: white;
    border: none;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    cursor: default;
}

.description-container {
    margin-top: 5px;
    max-height: 60px;
    overflow: hidden;
}

.description {
    font-size: 13px;
    color: #666;
    line-height: 1.4;
    display: -webkit-box;
    line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}

.tap-rating {
    display: flex;
    align-items: center;
    color: #ffb400;
    font-weight: bold;
}

.svg-icon {
    width: 18px;
    height: 18px;
    margin-right: 5px;
}

.rate-number-font {
    font-size: 16px;
}
</style>
