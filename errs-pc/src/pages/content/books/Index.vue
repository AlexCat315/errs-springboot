<script lang="ts" setup>
import { inject, onMounted, reactive, Ref, ref } from "vue";
import {
    get_book_top250_info,
    get_book_top50_info,
    get_book_welcome_info,
    get_book_hot_info,
} from "../../../net/book/get_book"; // 导入get_book_top250_info方法

import Sidebar from "./components/Sidebar.vue";
import BookList from "./components/BookList.vue";
import ViewAll from "./components/ViewAll.vue";
import Carousel from "./components/Carousel.vue"; // 引入轮播组件
import BookDetail from "./components/BookDetail.vue";

// 图片或视频及对应文字列表
const mediaList = [
    {
        type: "image",
        src: "https://www.alexcat.it.com/minio-api/public-errs/v2-b2a0002ce3b0703e98c29a6db9bc924d_r.jpg",
    },
    {
        type: "video",
        src: "https://www.alexcat.it.com/minio-api/public-errs/浮世绘-21323131ddasdsadsa31123.mp4",
    },
    {
        type: "image",
        src: "https://www.alexcat.it.com/minio-api/public-errs/2131232133131-maxresdefault.jpg",
    },
] as { type: "video" | "image"; src: string }[];

const textList = [
    {
        title: "三体-科幻雨果奖",
        description: "给岁月以文明，而不是给文明以岁月",
    },
    { title: "浮世绘", description: "日式美学新角度和切片。" },
    {
        title: "Les Misérables(悲惨世界)",
        description: "每个人出生都带着原罪。",
    },
];
const currentIndex = inject<Ref<number>>("currentIndex");
if (currentIndex === undefined) {
    throw new Error("currentIndex is undefined");
}
const handleCurrentIndexChange = (newIndex: number) => {
    if (newIndex === 0) {
        currentIndex.value = 36;
    } else if (newIndex === 1) {
        currentIndex.value = 71;
    } else if (newIndex === 2) {
        currentIndex.value = 72;
    }
};

interface EntertainmentItem {
    id: number;
    name: string;
    author: string;
    category: string[];
    description: string;
    rating: number;
    users?: number;
    categoryId: number;
    img: string;
    introduction: string;
    recommend: number;
}

interface Category {
    id: number;
    name: string;
}

// 初始化 state
const state = reactive({
    categories: [
        { id: 1, name: "经典 - 神作" },
        { id: 2, name: "Top 50高分榜单" },
        { id: 3, name: "大家都在看" },
        { id: 4, name: "火出圈外🔥" },
    ] as Category[],
    items: [] as EntertainmentItem[], // 初始化为空数组
});

// 根据类别筛选出推荐项
const getItemsByCategory = (categoryId: number) => {
    return state.items.filter((item) => item.categoryId === categoryId);
};

// 获取数据的方法
const fetchItemsTop250 = (start: number) => {
    get_book_top250_info(
        start,
        (response: any) => {
            // 检查返回的数据是否包含 `data` 字段，并且 `data` 是一个数组
            if (response && Array.isArray(response.data)) {
                // 给每个项目添加 categoryId 为 1，并只保留前6个项目
                const newItems = response.data
                    .slice(0, 6) // 只取前6个项目
                    .map((item: any) => ({
                        ...item, // 保留原始数据
                        categoryId: 1, // 添加 categoryId
                    }));
                state.items = [...state.items, ...newItems]; // 保留现有数据并添加新数据
                console.log(state.items); // 查看添加后的数据
            } else {
                console.error("返回的数据格式错误", response);
            }
        },
        (errorMessage: string) => {
            console.error(errorMessage); // 处理失败
        },
        (errorMessage: string) => {
            console.error(errorMessage); // 处理错误
        },
    );
};

// 获取数据的方法
const fetchItemsTop50 = (start: number) => {
    get_book_top50_info(
        start,
        (response: any) => {
            // 检查返回的数据是否包含 `data` 字段，并且 `data` 是一个数组
            if (response && Array.isArray(response.data)) {
                // 给每个项目添加 categoryId 为 2，并只保留前6个项目
                const newItems = response.data
                    .slice(0, 6) // 只取前6个项目
                    .map((item: any) => ({
                        ...item, // 保留原始数据
                        categoryId: 2, // 添加 categoryId
                    }));
                state.items = [...state.items, ...newItems]; // 保留现有数据并添加新数据
                console.log(state.items); // 查看添加后的数据
            } else {
                console.error("返回的数据格式错误", response);
            }
        },
        (errorMessage: string) => {
            console.error(errorMessage); // 处理失败
        },
        (errorMessage: string) => {
            console.error(errorMessage); // 处理错误
        },
    );
};

// 获取数据的方法
const fetchItemsWelcome = (start: number) => {
    get_book_welcome_info(
        start,
        (response: any) => {
            // 检查返回的数据是否包含 `data` 字段，并且 `data` 是一个数组
            if (response && Array.isArray(response.data)) {
                // 给每个项目添加 categoryId 为 2，并只保留前6个项目
                const newItems = response.data
                    .slice(0, 6) // 只取前6个项目
                    .map((item: any) => ({
                        ...item, // 保留原始数据
                        categoryId: 3, // 添加 categoryId
                    }));
                state.items = [...state.items, ...newItems]; // 保留现有数据并添加新数据
                console.log(state.items); // 查看添加后的数据
            } else {
                console.error("返回的数据格式错误", response);
            }
        },
        (errorMessage: string) => {
            console.error(errorMessage); // 处理失败
        },
        (errorMessage: string) => {
            console.error(errorMessage); // 处理错误
        },
    );
};

const fetchItemsHot = (start: number) => {
    get_book_hot_info(
        start,
        (response: any) => {
            // 检查返回的数据是否包含 `data` 字段，并且 `data` 是一个数组
            if (response && Array.isArray(response.data)) {
                // 给每个项目添加 categoryId 为 2，并只保留前6个项目
                const newItems = response.data
                    .slice(0, 6) // 只取前6个项目
                    .map((item: any) => ({
                        ...item, // 保留原始数据
                        categoryId: 4, // 添加 categoryId
                    }));
                state.items = [...state.items, ...newItems]; // 保留现有数据并添加新数据
            } else {
                console.error("返回的数据格式错误", response);
            }
        },
        (errorMessage: string) => {
            console.error(errorMessage); // 处理失败
        },
        (errorMessage: string) => {
            console.error(errorMessage); // 处理错误
        },
    );
};

// 在组件加载时请求数据
onMounted(() => {
    fetchItemsTop250(0);
    fetchItemsTop50(0);
    fetchItemsWelcome(0);
    fetchItemsHot(0);
});

const colors = [
    "#ec8236",
    "#f56c6c",
    "#67c23a",
    "#e6a23c",
    "#409eff",
    "#c239e6",
    "black",
    "#5941d3",
];
const colorsRandom = () => colors[Math.floor(Math.random() * colors.length)];

const globalShowBookList = inject<Ref<boolean>>("globalShowBookList");

const globalShowBookListSelect = inject<Ref<Number>>(
    "globalShowBookListSelect",
);

const showBookList = (id: Number) => {
    if (
        globalShowBookList !== undefined &&
        globalShowBookListSelect !== undefined
    ) {
        globalShowBookList.value = true;
        globalShowBookListSelect.value = id;
        console.log(globalShowBookListSelect.value);
    }
};
</script>

<template>
    <div v-if="currentIndex === null">
        <!-- 顶部 -->
        <div v-if="!globalShowBookList">
            <div>
                <div>
                    <p style="
                            font-size: 29px;
                            font-family: Arial, Helvetica, sans-serif;
                            font-weight: bold;
                            margin-left: 30px;
                        ">
                        图书
                    </p>
                </div>
                <div style="margin-top: 10px" class="divider-top"></div>
            </div>
            <!-- 轮播图或视频，左侧是文字，右侧是视频或者图片 -->
            <Carousel :mediaList="mediaList" :textList="textList" @currentIndexChanged="handleCurrentIndexChange" />

            <div class="entertainment-container">
                <!-- 推荐分类区块 -->
                <div v-for="category in state.categories" :key="category.id" class="category-section">
                    <div class="section-header">
                        <p style="
                                font-size: 19px;
                                font-family: Arial, Helvetica, sans-serif;
                                font-weight: bold;
                            ">
                            {{ category.name }}
                        </p>
                        <ViewAll @click="showBookList(category.id)" class="view-all" />
                    </div>
                    <div class="recommend-grid">
                        <div v-for="item in getItemsByCategory(category.id)" :key="item.id" class="recommend-card">
                            <div class="card-inner">
                                <!-- 正面内容 -->
                                <div class="card-front" :style="{
                                    backgroundImage:
                                        'url(' + item.img + ')',
                                }">
                                    <div class="overlay"></div>
                                    <div class="back-content">
                                        <h3 class="back-title">
                                            {{ item.name }}
                                        </h3>
                                        <p class="back-rating">
                                            <span class="score">{{
                                                item.rating
                                                }}</span>
                                            <span class="stars">
                                                {{
                                                    "★".repeat(
                                                        Math.round(
                                                            item.rating / 2,
                                                        ),
                                                    )
                                                }}{{
                                                    "☆".repeat(
                                                        5 -
                                                        Math.round(
                                                            item.rating / 2,
                                                        ),
                                                    )
                                                }}
                                            </span>
                                        </p>
                                        <button class="detail-btn">
                                            查看详情
                                        </button>
                                    </div>
                                </div>

                                <!-- 背面内容 -->
                                <div class="card-back">
                                    <div class="card-content">
                                        <div class="card-header">
                                            <span v-for="cat in item.category" :key="cat" :style="{
                                                background: colorsRandom(),
                                            }" class="category-tag">{{ cat }}</span>
                                            <div class="rating">
                                                <span class="score">{{
                                                    item.rating
                                                    }}</span>
                                                <div class="stars">
                                                    <span v-for="n in 5" :key="n" class="star">
                                                        {{
                                                            n <= Math.round(item.rating / 2,) ? "★" : "☆" }} </span>
                                                </div>
                                            </div>
                                        </div>
                                        <div style="margin-top: -20px">
                                            <h3 class="title">
                                                {{ item.name }}
                                            </h3>
                                            <p style="
                                                    margin-top: -9px;
                                                    color: #8a929b;
                                                    font-family: Tahoma, Geneva,
                                                        Verdana, sans-serif;
                                                ">
                                                {{ item.author }}
                                            </p>
                                        </div>

                                        <p class="description">
                                            {{ item.description }}
                                        </p>
                                        <div class="actions" style="
                                                padding-right: 20px;
                                                margin-bottom: 20px;
                                            ">
                                            <button class="get-btn">
                                                {{
                                                    "查看详情"
                                                }}
                                            </button>
                                            <span v-if="item.users" class="users">{{ item.users }}人评价</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="app" v-if="globalShowBookList">
            <Sidebar />
            <BookList />
        </div>
    </div>
    <!-- 书籍基本页 -->
    <div v-if="currentIndex !== null">
        <BookDetail />
    </div>
</template>

<style scoped>
* {
    box-sizing: border-box;
}

.entertainment-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 2rem;
}

.header {
    text-align: center;
    margin-bottom: 2rem;
}

.header p {
    color: #666;
    font-size: 1.1rem;
}

.section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 2rem 0;
}

.section-header h2 {
    font-size: 1.5rem;
    color: #333;
}

.view-all {
    font-family: Arial, Helvetica, sans-serif;
    text-decoration: none;
    font-size: 11px;
    height: 40px;
}

.recommend-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 1rem;
    margin-top: -20px;
    transform-origin: center;
    box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
}

.recommend-card {
    perspective: 1000px;
    height: 320px;
    transform: scale(0.8);
    cursor: pointer;
    border-radius: 9.6px;
    z-index: 10;
    box-shadow: inset 0px 4px 12px rgba(0, 0, 0, 0.1);
    border: 1px solid #ccc;
    /* 根据需要调整颜色和宽度 */
    overflow: visible;
}

.card-inner {
    position: relative;
    width: 100%;
    height: 100%;
    transition: transform 0.6s;
    transform-style: preserve-3d;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    transform-origin: center;
}

.recommend-card:hover .card-inner {
    transform: rotateY(180deg);
}

.card-front,
.card-back {
    position: absolute;
    width: 100%;
    height: 100%;
    backface-visibility: hidden;
    border-radius: 12px;
    overflow: hidden;
}

.card-front {
    background-size: 100% 100%;
    background-repeat: no-repeat;
    background-position: center;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 12px;
    z-index: 2;
}

.card-back {
    background: white;
    padding: 1.2rem;
    display: flex;
    flex-direction: column;
    transform: rotateY(180deg);
    z-index: 1;
}

.overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.6);
}

.back-content {
    position: relative;
    z-index: 2;
    color: white;
    text-align: center;
    padding: 1.5rem;
    border-radius: 12px;
}

.back-title {
    font-size: 1.5rem;
    margin-bottom: 1rem;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
    z-index: 2;
}

.back-rating {
    font-size: 1.2rem;
    margin: 1rem 0;
}

.back-rating .score {
    color: #ffb400;
    margin-right: 0.5rem;
}

.back-rating .stars {
    color: #ffb400;
    justify-content: center !important;
}

.detail-btn {
    background: rgba(255, 255, 255, 0.2);
    border: 2px;
    color: white;
    padding: 8px 24px;
    border-radius: 20px;
    cursor: pointer;
    transition: all 0.3s;
    margin-top: 1.5rem;
}

.detail-btn:hover {
    background: rgba(255, 255, 255, 0.2);
}

.card-content {
    height: 110%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.title {
    font-size: 1.2rem;
    color: #333;
    font-family: Arial, Helvetica, sans-serif;
}

.description {
    color: #666;
    font-size: 0.95rem;
    display: -webkit-box;
    -webkit-line-clamp: 5;
    line-clamp: 5;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
    max-height: calc(1.4em * 4);
    font-family: Arial, Helvetica, sans-serif;
    font-size: 14px;
    margin-top: -26px !important;
}

.actions {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: -20px !important;
}

.get-btn {
    background: #007aff;
    color: white;
    border: none;
    padding: 8px 16px;
    border-radius: 6px;
    cursor: pointer;
    transition: background 0.2s;
}

.get-btn:hover {
    background: #0063cc;
}

.users {
    font-size: 0.85rem;
    color: #888;
}

.rating {
    text-align: right;
    background: rgba(0, 0, 0, 0.6);
    padding: 4px 8px;
    border-radius: 4px;
    color: white;
    width: 50%;
    margin-left: 130px;
}

.score {
    font-size: 1.1rem;
    margin-right: 4px;
}

.stars {
    color: #ffb400;
}

.category-tag {
    display: inline-block;
    margin: 2px 4px;
    padding: 4px 8px;
    background-color: #ec8236;
    border-radius: 4px;
    font-size: 14px;
    color: #fff;
    white-space: normal;
    text-align: center;
    margin-left: -5px;
}

.card-header {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    align-items: center;
    background-color: #ffffff;
}

.card {
    width: 100%;
    max-width: 600px;
    margin: 16px auto;
    border: 1px solid #e5e7eb;
    border-radius: 8px;
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
}

.rating {
    display: flex;
    align-items: center;
    gap: 4px;
    background: transparent;
    padding: 0;
    margin-left: 0;
}

.score {
    font-size: 1rem;
    color: #ffb400;
    font-weight: 600;
}

.stars {
    display: flex;
    gap: 1px;
}

.star {
    font-size: 0.9rem;
    color: #ffb400;
}

.divider {
    height: 1px;
    background-color: #ccc;
    margin-top: 40px;
    margin-bottom: -20px;
}

.divider-top {
    height: 1px;
    background-color: #ccc;
    margin-top: -30px;
    margin-bottom: 50px;
}

.app {
    display: flex;
    height: 100%;
    height: 100vh;
    /* 使用视口高度 */
    overflow: hidden;
    /* 防止整个容器滚动 */
}
</style>
