<script lang="ts" setup>
import { ref, inject, Ref, onMounted } from "vue";
import SearchIndex from "./search/SearchIndex.vue";

const currentIndex = inject<Ref<number | null>>("currentIndex");
if (currentIndex === undefined) {
    throw new Error("Function not implemented.");
}

const globalTheme = inject<string>("globalTheme");

const showErrorpanle = ref(false);
const errorPanleMsg = ref("");

</script>

<template>
    <div>
        <div :style="{ background: globalTheme === 'dark' ? '#3d3d44' : '#FFF' }" class="top-bar">
            <p :style="{ color: globalTheme === 'dark' ? '#FFF' : '#707070' }" style="font-family: Arial, Helvetica, sans-serif;font-size: 14px;"> 
                搜索
            </p>
            <button class="top-button">
                <svg t="1738767589842" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="5331" width="20" height="20">
                    <path
                        d="M365.604792 262.10044l127.792525-127.793548 0.152473 503.227196c0.004093 11.014868 8.934486 19.943215 19.949354 19.943215 0.002047 0 0.004093 0 0.00614 0 11.016915-0.004093 19.947308-8.937556 19.943215-19.954471l-0.152473-503.100306 127.675868 127.676891c3.89675 3.89675 8.999978 5.843078 14.106276 5.843078 5.104251 0 10.212596-1.948375 14.106276-5.842055 7.79043-7.79043 7.79043-20.422122 0-28.212552L527.85306 72.555479c-3.638877-3.870144-8.791224-6.300496-14.521738-6.300496-0.002047 0-0.004093 0-0.00614 0-0.00614 0-0.01228 0.001023-0.01842 0.001023-0.00614 0-0.01228-0.001023-0.01842-0.001023-5.291516 0-10.364045 2.101871-14.106276 5.842055L337.39224 233.886865c-7.789407 7.79043-7.789407 20.422122 0 28.212552C345.18267 269.89087 357.814362 269.89087 365.604792 262.10044zM782.507924 315.087235 642.713041 315.087235c-11.016915 0-19.949354 8.931416-19.949354 19.949354s8.93244 19.949354 19.949354 19.949354l139.794883 0c38.498826 0 69.821205 31.322379 69.821205 69.821205l0 425.443645c0 38.498826-31.322379 69.821205-69.821205 69.821205L246.009973 920.071999c-38.498826 0-69.821205-31.322379-69.821205-69.821205L176.188768 424.806125c0-38.498826 31.322379-69.821205 69.821205-69.821205l143.936203 0c11.017938 0 19.949354-8.931416 19.949354-19.949354s-8.931416-19.949354-19.949354-19.949354L246.009973 315.086212c-60.499909 0-109.718891 49.220005-109.718891 109.718891l0 425.443645c0 60.499909 49.220005 109.718891 109.718891 109.718891l536.49795 0c60.499909 0 109.718891-49.220005 109.718891-109.718891L892.226814 424.806125C892.227838 364.306216 843.007833 315.087235 782.507924 315.087235z"
                        :fill="globalTheme === 'dark' ? '#FFF' : '#707070'" p-id="5332"></path>
                </svg>
            </button>
        </div>
        <SearchIndex />
    </div>
</template>

<style scoped>
.top-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
    height: 60px;
    background: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.top-button {
    background: none;
    border-radius: 4px;
    cursor: pointer;
    border: none;
}

.top-button:hover {
    background: rgba(0, 0, 0, 0.1);
}

.book-card {
    display: flex;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 0 6px rgba(0, 0, 0, 0.2);
    overflow: hidden;
    width: 88%;
    margin: 20px auto;
    font-family: Arial, Helvetica, sans-serif;
}

.comment-card {
    display: flex;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 0 6px rgba(0, 0, 0, 0.2);
    overflow: hidden;
    width: 88%;
    margin: 20px auto;
    font-family: Arial, Helvetica, sans-serif;
}

.comment-comment {
    margin: 20px;
}

.book-cover img {
    width: 150px;
    height: 200px;
    object-fit: cover;
    border-radius: 8px;
    margin-right: 20px;
    margin-left: 20px;
    margin-top: 40px;
}

.book-info {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding: 15px;
    width: 100%;
}

h3 {
    font-size: 1.5rem;
    font-weight: bold;
    margin: 0;
}

.author {
    color: #555;
    font-size: 13px;
    margin-top: -10px;
    margin-bottom: 5px;
}

.category {
    color: #888;
    font-size: 1rem;
    margin: 5px 0;
}

.description {
    color: #333;
    font-size: 0.95rem;
    margin: 10px 0;
    line-height: 1.4;
    display: -webkit-box;
    -webkit-line-clamp: 5;
    line-clamp: 5;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}

.rating,
.recommend {
    display: flex;
    align-items: center;
    margin-top: 10px;
}

.rating-score {
    font-weight: bold;
    margin-right: 10px;
}

.rating-count {
    color: #888;
    font-size: 0.9rem;
}

.recommend {
    margin-left: 10px;
}

.recommend span {
    font-size: 13px;
    font-weight: 500;
    margin-top: -2px;
}

.book-title-top {
    display: flex;
}

.book_rating_item_label_number_image.book_rating_item_label_ListItem {
    height: 22px;
}

.book_rating_item_label_number.book_rating_item_label_ListItem,
.book_rating_item_label_number_image.book_rating_item_label_ListItem,
.book_rating_item_label_text.book_rating_item_label_ListItem {
    flex-shrink: 0;
    margin-left: 8px;
}

@keyframes moveUp {
    0% {
        transform: translateY(0);
    }

    100% {
        transform: translateY(-20px);
    }
}

.error-msg {
    background-color: rgba(0, 0, 0, 0.7);
    color: #fff;
    width: 150px;
    height: 30px;
    /* 建议显式设置高度 */
    display: flex;
    /* 启用 Flex 布局 */
    align-items: center;
    /* 垂直居中 */
    justify-content: center;
    /* 水平居中 */
    font-family: Arial, Helvetica, sans-serif;
    position: fixed;
    z-index: 4;
    left: 50%;
    transform: translateX(-50%);
    margin-top: 30px;
    animation: moveUp 0.4s ease-in-out forwards;
    font-size: 13px;
    border-radius: 8px;
}

:disabledscore {
    pointer-events: none;
    /* 禁止鼠标事件 */
    cursor: not-allowed;
    /* 改变鼠标指针样式 */
    opacity: 0.6;
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
</style>
