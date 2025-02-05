<template>
    <div style="display: flex; justify-content: space-between; padding: 20px">
        <button class="top-button" @click="goBack()">
            <svg
                t="1738767542422"
                class="icon"
                viewBox="0 0 1024 1024"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                p-id="4184"
                width="18"
                height="18"
            >
                <path
                    d="M314.257949 511.802956c107.013345 97.707837 211.700314 193.089297 316.387282 288.470757l160.520018 146.561755c23.263771 20.937394 27.916525 44.201164 11.631885 62.812181-18.611017 20.937394-41.874787 18.611017-65.138558-4.652754-162.846395-144.235378-321.040036-290.797134-481.560054-435.032512-9.305508-9.305508-18.611017-16.28464-27.916525-23.263771-20.937394-18.611017-20.937394-48.853919-2.326377-65.138558L602.728706 139.582624c44.201164-41.874787 90.728706-81.423198 134.92987-123.297984 23.263771-20.937394 51.180296-18.611017 65.138558 4.652754 11.631885 18.611017 6.979131 39.54841-13.958262 60.485804C674.846395 186.110166 560.853919 290.797134 444.535065 395.484102c-41.874787 34.895656-83.749575 74.444066-130.277116 116.318854z"
                    p-id="4185"
                    fill="#707070"
                ></path>
            </svg>
        </button>
        <button class="top-button">
            <svg
                t="1738767589842"
                class="icon"
                viewBox="0 0 1024 1024"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                p-id="5331"
                width="20"
                height="20"
            >
                <path
                    d="M365.604792 262.10044l127.792525-127.793548 0.152473 503.227196c0.004093 11.014868 8.934486 19.943215 19.949354 19.943215 0.002047 0 0.004093 0 0.00614 0 11.016915-0.004093 19.947308-8.937556 19.943215-19.954471l-0.152473-503.100306 127.675868 127.676891c3.89675 3.89675 8.999978 5.843078 14.106276 5.843078 5.104251 0 10.212596-1.948375 14.106276-5.842055 7.79043-7.79043 7.79043-20.422122 0-28.212552L527.85306 72.555479c-3.638877-3.870144-8.791224-6.300496-14.521738-6.300496-0.002047 0-0.004093 0-0.00614 0-0.00614 0-0.01228 0.001023-0.01842 0.001023-0.00614 0-0.01228-0.001023-0.01842-0.001023-5.291516 0-10.364045 2.101871-14.106276 5.842055L337.39224 233.886865c-7.789407 7.79043-7.789407 20.422122 0 28.212552C345.18267 269.89087 357.814362 269.89087 365.604792 262.10044zM782.507924 315.087235 642.713041 315.087235c-11.016915 0-19.949354 8.931416-19.949354 19.949354s8.93244 19.949354 19.949354 19.949354l139.794883 0c38.498826 0 69.821205 31.322379 69.821205 69.821205l0 425.443645c0 38.498826-31.322379 69.821205-69.821205 69.821205L246.009973 920.071999c-38.498826 0-69.821205-31.322379-69.821205-69.821205L176.188768 424.806125c0-38.498826 31.322379-69.821205 69.821205-69.821205l143.936203 0c11.017938 0 19.949354-8.931416 19.949354-19.949354s-8.931416-19.949354-19.949354-19.949354L246.009973 315.086212c-60.499909 0-109.718891 49.220005-109.718891 109.718891l0 425.443645c0 60.499909 49.220005 109.718891 109.718891 109.718891l536.49795 0c60.499909 0 109.718891-49.220005 109.718891-109.718891L892.226814 424.806125C892.227838 364.306216 843.007833 315.087235 782.507924 315.087235z"
                    fill="#707070"
                    p-id="5332"
                ></path>
            </svg>
        </button>
    </div>
    <div class="book-detail">
        <div class="book-info">
            <div class="book-cover">
                <img :src="book.coverUrl" :alt="book.title" />
            </div>
            <div class="book-meta">
                <h1>{{ book.title }}</h1>
                <p>作者: {{ book.author }}</p>
                <p>出版社: {{ book.publisher }}</p>
                <p>出版日期: {{ book.publishDate }}</p>
                <p>ISBN: {{ book.isbn }}</p>
                <p>类别: {{ book.category }}</p>
            </div>
        </div>

        <div class="book-rating">
            <div class="rating-score">
                <span class="score">{{ book.rating }}</span>
                <el-rate
                    v-model="userRating"
                    :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                    @change="handleRating"
                >
                </el-rate>
                <span class="rating-count">({{ book.ratingCount }}人评分)</span>
            </div>
        </div>

        <div class="book-intro">
            <h2>内容简介</h2>
            <p>{{ book.description }}</p>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, inject, Ref } from "vue";

interface Book {
    title: string;
    author: string;
    publisher: string;
    publishDate: string;
    isbn: string;
    category: string;
    coverUrl: string;
    rating: number;
    ratingCount: number;
    description: string;
}

const book = ref<Book>({
    title: "三体",
    author: "刘慈欣",
    publisher: "重庆出版社",
    publishDate: "2008-01-01",
    isbn: "9787536692968",
    category: "科幻小说",
    coverUrl: "/images/book-cover.jpg",
    rating: 9.3,
    ratingCount: 1234,
    description:
        '文化大革命如火如荼进行的同时，军方探寻外星文明的绝秘计划"红岸工程"取得了突破性进展...',
});

const userRating = ref<number>(0);

function handleRating(value: number) {
    // 处理用户评分
    console.log("用户评分:", value);
}

const currentIndex = inject<Ref<number>>("currentIndex");

const goBack = () => {
    currentIndex.value = null;
};
</script>

<style scoped>
.book-detail {
    padding: 20px;
}

.book-info {
    display: flex;
    margin-bottom: 30px;
}

.book-cover {
    width: 200px;
    margin-right: 30px;
}

.book-cover img {
    width: 100%;
    height: auto;
}

.book-meta h1 {
    margin-top: 0;
    margin-bottom: 20px;
}

.book-meta p {
    margin: 10px 0;
    color: #666;
}

.book-rating {
    margin-bottom: 30px;
}

.rating-score {
    display: flex;
    align-items: center;
}

.rating-score .score {
    font-size: 28px;
    color: #ff9900;
    margin-right: 15px;
}

.rating-count {
    margin-left: 10px;
    color: #999;
}

.book-intro h2 {
    margin-bottom: 15px;
}

.book-intro p {
    line-height: 1.6;
    color: #333;
}

.top-button {
    background: none; /* 使用 none 完全移除背景 */
    border-radius: 4px;
    cursor: pointer;
    border: none; /* 确保没有边框 */
}
.top-button:hover {
    background: rgba(0, 0, 0, 0.1); /* 悬停时背景色 */
}
</style>
