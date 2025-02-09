<script lang="ts" setup>
import {inject, onMounted, Ref, ref} from "vue";
import {get_book_detail} from "../../../../../net/book/get_book";
import Like from "./Like.vue";
import UnLike from "./UnLike.vue";
import {validate_token} from "../../../../../net/account/validate_token";
import {validate_like} from "../../../../../net/book/validate_like";
import Star from "./Star.vue";
import GoLink from "./GoLink.vue";
import Score from "./Score.vue";
import StarScore from "./StarScore.vue";
import Cancel from "./Cancel.vue";
import {open} from "@tauri-apps/plugin-shell";
import {insert_score, validate_score} from "../../../../../net/book/score";
import UpdateStar from "./UpdateStar.vue";
import Comment from "./Comment.vue";
import EditComment from "./EditComment.vue";
import LookAll from "./LookAll.vue";

interface Book {
    id: number;
    name: string;
    author: string;
    category: string[];
    description: string;
    rating: number;
    users?: number;
    img: string;
    introduction: string;
    recommend: number;
}

const book = ref<Book>({
    id: 0,
    name: "",
    author: "",
    category: [],
    description: "",
    rating: 0,
    users: 0,
    img: "",
    introduction: "",
    recommend: 0,
});

const currentIndex = inject<Ref<number | null>>("currentIndex");
if (currentIndex === undefined) {
    throw new Error("Function not implemented.");
}

const globalTheme = inject<string>("globalTheme");

const getBookDetail = () => {
    // 获取书籍详情
    get_book_detail(
        currentIndex.value!,
        (data: any) => {
            book.value = data.data;
        },
        (failure: string) => {
            console.log(failure);
        },
        (error: string) => {
            console.log(error);
        },
    );
};

onMounted(() => {
    getBookDetail();
});

const imgSrc = (book: Book) => {
    if (book.recommend >= 90) {
        return "https://www.alexcat.it.com/minio-api/public-errs/newRatings_900.png";
    } else if (book.recommend >= 87) {
        return "https://www.alexcat.it.com/minio-api/public-errs/newRatings_870.png";
    } else if (book.recommend >= 85) {
        return "https://www.alexcat.it.com/minio-api/public-errs/newRatings_850.png";
    } else if (book.recommend >= 80) {
        return "https://www.alexcat.it.com/minio-api/public-errs/newRatings_800.png";
    } else if (book.recommend >= 70) {
        return "https://www.alexcat.it.com/minio-api/public-errs/newRatings_700.png";
    }
};

// 检查用户是否登录
// 如果没有登录，显示的就是灰色的收藏按钮
const showLike = ref(false);
const cheekToken = () => {
    // 检测是否有token
    const token = ref(localStorage.getItem("token"));
    if (
        token.value !== null &&
        token.value !== "" &&
        token.value !== undefined
    ) {
        validate_token(
            token.value,
            (data: any) => {
                if (data.code === 200) {
                    validate_like(
                        currentIndex.value,
                        (data: any) => {
                            if (data.code === 200) {
                                showLike.value = data.data;
                            }
                        },
                        (message: string) => {
                            console.log("failure:", message);
                        },
                        (message: string) => {
                            console.log("error:", message);
                        },
                    );
                }
            },
            (message: string) => {
                localStorage.removeItem("token");
                console.log("failure:", message);
            },
            (message: string) => {
                console.log("error:", message);
            },
        );
    }
};

onMounted(() => {
    cheekToken();
    ValidateScore();
});

const cancelLike = () => {
    showLike.value = false;
};
const addLike = () => {
    showLike.value = true;
};

const openLink = () => {
    // console.log("open link");
    open("https://weread.qq.com/");
    // window.open('https://weread.qq.com/');
};

const scoreMessage = ref("评价");

const showErrorpanle = ref(false);
const errorPanleMsg = ref("");
const showStarScore = ref(false);

const selectedScore = ref(null);

const handleRatingChange = (value: number) => {
    selectedScore.value = value * 2;
    insert_score(
        currentIndex.value,
        selectedScore.value,
        (data: any) => {
            console.log(data);
            showStarScore.value = false;
            showErrorpanle.value = true;
            errorPanleMsg.value = "评分成功";
            scoreMessage.value = "已评价";
            setTimeout(() => {
                showErrorpanle.value = false;
            }, 2000);
        },
        (message: string) => {
            showStarScore.value = false;
            showErrorpanle.value = true;
            errorPanleMsg.value = "发生错误";
            setTimeout(() => {
                showErrorpanle.value = false;
            }, 2000);
            console.log("failure:", message);
        },
    );
};

const goBack = () => {
    currentIndex.value = null;
};

const ValidateScore = () => {
    validate_score(
        currentIndex.value,
        (data: any) => {
            console.log(data);
        },
        (message: string) => {
            scoreMessage.value = "已评价";
            console.log("failure:", message);
        },
        (message: string) => {
            scoreMessage.value = "已评价";
            console.log("error:", message);
        },
    );
};
const showEvaluated = () => {
    showLoading.value = true;
};
const showLoading = ref(false);
const updateScore = (value: boolean) => {
    showLoading.value = value;
    ValidateScore();
    showStarScore.value = value;
};
</script>

<template>
    <div>
        <p v-if="showErrorpanle" class="error-msg">{{ errorPanleMsg }}</p>
        <!-- Loading 遮罩层 -->
        <div v-if="showLoading" class="loading-overlay">
            <UpdateStar
                @update:modelValue="updateScore"
                style="margin-left: 200px; margin-top: -120px"
            />
        </div>
        <div
            :style="{ background: globalTheme === 'dark' ? '#3d3d44' : '#FFF' }"
            class="top-bar"
        >
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
                        :fill="globalTheme === 'dark' ? '#FFF' : '#707070'"
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
                        :fill="globalTheme === 'dark' ? '#FFF' : '#707070'"
                        p-id="5332"
                    ></path>
                </svg>
            </button>
        </div>

        <!-- 书籍基本信息卡片 -->
        <div
            :style="{ background: globalTheme === 'dark' ? '#3d3d44' : '#FFF' }"
            class="book-card"
        >
            <div class="book-cover">
                <img :src="book.img" alt="cover" />
            </div>
            <div class="book-info">
                <div class="book-title-top">
                    <div
                        style="
                            display: flex;
                            align-items: center;
                            justify-content: space-between;
                            width: 100%;
                        "
                    >
                        <!-- 左边的元素 -->
                        <div style="display: flex; align-items: center">
                            <h4
                                :style="{
                                    color:
                                        globalTheme === 'dark'
                                            ? '#FFF'
                                            : '#000',
                                }"
                                style="
                                    margin-right: 10px;
                                    font-size: 18px;
                                    font-weight: bold;
                                "
                            >
                                {{ book.name }}
                            </h4>
                            <img
                                :src="imgSrc(book)"
                                class="book_rating_item_label_number_image book_rating_item_label_ListItem"
                                style="height: 22px; margin-right: 10px"
                            />
                        </div>

                        <!-- 右边的元素 -->
                        <div style="display: flex; align-items: center">
                            <div
                                v-if="!showStarScore"
                                style="display: flex; align-items: center"
                            >
                                <Like v-if="!showLike" @click="addLike()" />
                                <UnLike v-if="showLike" @click="cancelLike()" />
                                <GoLink
                                    @click="openLink()"
                                    style="height: 40px; transform: scale(0.85)"
                                />
                                <Score
                                    v-if="scoreMessage === '评价'"
                                    @click="showStarScore = true"
                                    :score="scoreMessage"
                                    style="height: 40px; transform: scale(0.7)"
                                />
                                <Score
                                    @click="showEvaluated()"
                                    v-if="scoreMessage === '已评价'"
                                    :score="scoreMessage"
                                    style="height: 40px; transform: scale(0.7)"
                                />
                            </div>
                            <div
                                v-if="showStarScore"
                                style="
                                    display: flex;
                                    align-items: center;
                                    background-color: #fff;
                                    border-radius: 60px;
                                    box-shadow: 0 0 6px rgba(0, 0, 0, 0.2);
                                    height: 38px;
                                    margin-top: 4px;
                                    padding-bottom: 4px;
                                "
                            >
                                <StarScore
                                    @update:modelValue="handleRatingChange"
                                    style="transform: scale(0.7)"
                                />
                                <Cancel
                                    @click="showStarScore = false"
                                    style="
                                        margin-left: -15px;
                                        margin-top: 8px;
                                        margin-right: 10px;
                                    "
                                />
                            </div>
                        </div>
                    </div>
                </div>

                <p
                    :style="{
                        color: globalTheme === 'dark' ? '#8f8f8f' : ' #555',
                    }"
                    class="author"
                >
                    {{ book.author }}
                </p>
                <p
                    :style="{
                        color: globalTheme === 'dark' ? '#8f8f8f' : ' #555',
                    }"
                    class="description"
                >
                    {{ book.description }}
                </p>
                <div style="display: flex; margin-bottom: 10px">
                    <Star :score="book.rating" style="margin-top: 10px" />
                    <div class="rating">
                        <span
                            :style="{
                                color:
                                    globalTheme === 'dark' ? '#FFF' : 'black',
                            }"
                            class="rating-score"
                            >{{ book.rating }}分</span
                        >
                        <span class="rating-count"
                            >{{ book.users }} 人评价</span
                        >
                    </div>
                    <div
                        :style="{
                            color: globalTheme === 'dark' ? '#FFF' : 'black',
                        }"
                        class="recommend"
                    >
                        <span>推荐指数：</span>
                        <span>{{ book.recommend }}%</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- 书籍评论 -->
        <div
            :style="{
                background: globalTheme === 'dark' ? '#141111' : '#fff',
            }"
            class="comment-card"
        >
            <Comment class="comment-comment" />
            <Comment class="comment-comment" />
            <LookAll />
        </div>

        <div style="display: flex">
            <EditComment />
        </div>
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
    pointer-events: none; /* 禁止鼠标事件 */
    cursor: not-allowed; /* 改变鼠标指针样式 */
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
