<template>
    <div class="entertainment-container">
        <!-- 推荐分类区块 -->
        <div
            v-for="category in state.categories"
            :key="category.id"
            class="category-section"
        >
            <div class="section-header">
                <p style="font-size: 23px; font-family: yousu-title-black">
                    {{ category.name }}
                </p>
                <a class="view-all" href="#">查看全部</a>
            </div>

            <div class="recommend-grid">
                <div
                    v-for="item in getItemsByCategory(category.id)"
                    :key="item.id"
                    class="recommend-card"
                >
                    <div class="card-inner">
                        <!-- 正面内容 -->
                        <div class="card-front">
                            <div class="card-content">
                                <div class="card-header">
                                    <span
                                        v-for="cat in item.category"
                                        :key="cat"
                                        :style="{ background: colorsRandom() }"
                                        class="category-tag"
                                        >{{ cat }}</span
                                    >
                                    <div class="rating">
                                        <span class="score">{{
                                            item.rating
                                        }}</span>
                                        <div class="stars">
                                            <span
                                                v-for="n in 5"
                                                :key="n"
                                                class="star"
                                            >
                                                {{
                                                    n <=
                                                    Math.round(item.rating / 2)
                                                        ? "★"
                                                        : "☆"
                                                }}
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div style="margin-top: -20px">
                                    <h3 class="title">{{ item.name }}</h3>
                                    <p
                                        style="
                                            margin-top: -9px;
                                            color: #8a929b;
                                            font-family:
                                                Tahoma, Geneva, Verdana,
                                                sans-serif;
                                        "
                                    >
                                        {{ item.author }}
                                    </p>
                                </div>

                                <p class="description">
                                    {{ item.description }}
                                </p>
                                <div
                                    class="actions"
                                    style="
                                        padding-right: 20px;
                                        margin-bottom: 20px;
                                    "
                                >
                                    <button class="get-btn">
                                        {{
                                            item.price
                                                ? `¥${item.price}`
                                                : "立即体验"
                                        }}
                                    </button>
                                    <span v-if="item.users" class="users"
                                        >{{ item.users }}人评价</span
                                    >
                                </div>
                            </div>
                        </div>

                        <!-- 背面内容 -->
                        <div
                            :style="{
                                backgroundImage: 'url(' + item.img + ')',
                            }"
                            class="card-back"
                        >
                            <div class="overlay"></div>
                            <div class="back-content">
                                <h3 class="back-title">{{ item.name }}</h3>
                                <p class="back-rating">
                                    <span class="score">{{ item.rating }}</span>
                                    <span class="stars">
                                        {{
                                            "★".repeat(
                                                Math.round(item.rating / 2),
                                            )
                                        }}{{
                                            "☆".repeat(
                                                5 - Math.round(item.rating / 2),
                                            )
                                        }}
                                    </span>
                                </p>
                                <button class="detail-btn">查看详情</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive } from "vue";
import { get_book_top250_info } from "../../../net/explore/get_book"; // 导入validate_email方法

interface EntertainmentItem {
    id: number;
    name: string;
    author: string;
    category: string[];
    description: string;
    rating: number;
    price?: number;
    users?: number;
    categoryId: number;
    img: string;
    introduction: string;
}

interface Category {
    id: number;
    name: string;
}

// 初始化 state
const state = reactive({
    categories: [
             { id: 1, name: "精典书籍推荐" },
        { id: 2, name: "热门电影推荐" },
        { id: 3, name: "精选游戏合集" },
        { id: 4, name: "高分剧集榜单" },
 
    ] as Category[],
    items: [] as EntertainmentItem[], // 初始化为空数组
});

// 根据类别筛选出推荐项
const getItemsByCategory = (categoryId: number) => {
    return state.items.filter((item) => item.categoryId === categoryId);
};

// 获取数据的方法
const fetchItems = (start: number) => {
    get_book_top250_info(
        start,
        (response: any) => {
            // 检查返回的数据是否包含 `data` 字段，并且 `data` 是一个数组
            if (response && Array.isArray(response.data)) {
                // 给每个项目添加 categoryId 为 3，并只保留前6个项目
                state.items = response.data
                    .slice(0, 6) // 只取前6个项目
                    .map((item: any) => ({
                        ...item, // 保留原始数据
                        categoryId: 1, // 添加 categoryId
                    }));
                console.log(state.items); // 查看添加后的数据
            } else {
                console.error("返回的数据格式错误", response);
                state.items = []; // 如果数据格式不正确，设置为空数组
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
    fetchItems(0); // 假设请求第一页数据
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
</script>

<style scoped>
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
    color: #007aff;
    text-decoration: none;
    font-size: 0.95rem;
}

.recommend-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 1rem;
    margin-top: -20px;
    transform-origin: center;
    box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
    overflow: hidden; /* 确保内容不会溢出边界 */
}

.recommend-card {
    perspective: 1000px;
    height: 320px;
    transform: scale(0.8);
    cursor: pointer;
    border-radius: 9.6px;
    box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1) !important; /* 添加阴影效果 */
}

.card-inner {
    position: relative;
    width: 100%;
    height: 100%;
    transition: transform 0.6s;
    transform-style: preserve-3d;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
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
    border-radius: 12px;
}

.card-front {
    background: white;
    padding: 1.2rem;
    display: flex;
    flex-direction: column;
}

.card-back {
    /* background-size: cover; */
    background-size: 100% 100%; /* 关键属性 */
    background-repeat: no-repeat;
    background-position: center;
    transform: rotateY(180deg);
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 12px;
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
}

.detail-btn {
    background: transparent;
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

/* 公共样式 */
.card-content {
    height: 110%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.title {
    font-size: 1.2rem;
    color: #333;
    /* margin: 0.5rem 0; */
    font-family: Arial, Helvetica, sans-serif;
}

.description {
    color: #666;
    font-size: 0.95rem;
    display: -webkit-box; /* 必须，开启弹性盒子布局 */
    -webkit-line-clamp: 5; /* 设置显示的行数为 6 行 */
    line-clamp: 5;
    -webkit-box-orient: vertical; /* 必须，设置盒子方向为垂直 */
    overflow: hidden; /* 必须，隐藏多余的内容 */
    text-overflow: ellipsis; /* 添加省略号 */
    max-height: calc(1.4em * 4); /* 设置高度为 6 行高度 */
    font-family: wenquanyimihei;
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
      margin-top: -20px !important;
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
    display: inline-block; /* 确保标签为块状元素 */
    margin: 2px 4px; /* 设置标签的上下左右间距 */
    padding: 4px 8px; /* 设置标签的内边距 */
    background-color: #ec8236; /* 背景颜色，可根据需求调整 */
    border-radius: 4px; /* 设置标签的圆角 */
    font-size: 14px; /* 字体大小 */
    color: #fff; /* 字体颜色 */
    white-space: normal; /* 允许换行 */
    text-align: center; /* 标签文字居中 */
    margin-left: -5px;
}

.card-header {
    display: flex; /* 使用 flex 布局 */
    flex-wrap: wrap; /* 允许标签换行 */
    gap: 8px; /* 设置标签之间的间距 */
    align-items: center; /* 垂直对齐标签内容 */
    background-color: #ffffff; /* 父级容器背景色，可调整 */
}

.card {
    width: 100%; /* 父容器宽度，默认占满父级 */
    max-width: 600px; /* 限制最大宽度 */
    margin: 16px auto; /* 设置外边距，居中显示 */
    border: 1px solid #e5e7eb; /* 设置边框颜色 */
    border-radius: 8px; /* 设置容器圆角 */
    box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
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
</style>
