<script lang="ts" setup>
import { onMounted, ref, inject, Ref, watch } from "vue";
import {
    get_book_top250_info,
    get_book_top50_info,
    get_book_welcome_info,
    get_book_hot_info,
} from "../../../../net/book/get_book";

const globalTheme = inject<string>("globalTheme");
if (globalTheme === undefined) {
    throw new Error("Function not implemented.");
}

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

const books = ref<Book[]>([]);
const globalShowBookListSelect = inject<Ref<number>>(
    "globalShowBookListSelect",
);

const mergeBooks = (newBooks: Book[]) => {
    const existingIds = new Set(books.value.map((book) => book.id));
    const uniqueBooks = newBooks.filter((book) => !existingIds.has(book.id));
    books.value = [...books.value, ...uniqueBooks];
};

const fetchItemsTop250 = (start: number) => {
    get_book_top250_info(
        start,
        (response: any) => {
            // 检查返回的数据是否包含 `data` 字段，并且 `data` 是一个数组
            if (response && Array.isArray(response.data)) {
                mergeBooks(response.data);
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
                mergeBooks(response.data);
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
                mergeBooks(response.data);
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
                mergeBooks(response.data);
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
if (globalShowBookListSelect === undefined) {
    throw new Error("globalShowBookListSelect is undefined");
}

// 在组件加载时请求数据
onMounted(() => {
    switch (globalShowBookListSelect.value) {
        case 1:
            fetchItemsTop250(0);
            break;
        case 2:
            fetchItemsTop50(0);
            break;
        case 3:
            fetchItemsWelcome(0);
            break;
        case 4:
            fetchItemsHot(0);
            break;
        default:
            break;
    }
});

// 监听器，监听globalShowBookListSelect 的变化，当变化时重新请求数据
watch(globalShowBookListSelect, (value) => {
    books.value = [];
    switch (value) {
        case 1:
            fetchItemsTop250(0);
            break;
        case 2:
            fetchItemsTop50(0);
            break;
        case 3:
            fetchItemsWelcome(0);
            break;
        case 4:
            fetchItemsHot(0);
            break;
        default:
            break;
    }
});

const scrollContainer = ref(null);

const handleScroll = () => {
    if (!scrollContainer.value) return;

    const { scrollTop, scrollHeight, clientHeight } = scrollContainer.value;
    const isBottom = scrollTop + clientHeight >= scrollHeight - 5;

    if (isBottom) {
        switch (globalShowBookListSelect.value) {
            case 1:
                fetchItemsTop250(books.value.length);
                break;
            case 2:
                fetchItemsTop50(books.value.length);
                console.log(books.value.length);
                break;
            case 3:
                fetchItemsWelcome(books.value.length);
                break;
            case 4:
                fetchItemsHot(books.value.length);
                break;
            default:
                break;
        }
    }
};

const imgSrc = (book: Book) => {
    if (book.recommend >= 90) {
        return "https://www.alexcat.it.com/minio-api/public-errs/newRatings_900.png";
    } else if (book.recommend >= 87) {
        return "https://www.alexcat.it.com/minio-api/public-errs/newRatings_870.png";
    } else if (book.recommend >= 85) {
        return "https://www.alexcat.it.com/minio-api/public-errs/newRatings_850.png";
    } else if (book.recommend >= 800) {
        return "https://www.alexcat.it.com/minio-api/public-errs/newRatings_800.png";
    } else if (book.recommend >= 70) {
        return "https://www.alexcat.it.com/minio-api/public-errs/newRatings_700.png";
    }
};
</script>

<template>
    <div ref="scrollContainer" @scroll="handleScroll" class="book-list">
        <div v-for="(book, index) in books" :key="index" class="book-item">
            <p class="index">{{ index + 1 }}</p>
            <img :src="book.img" alt="Book cover" class="book-cover" />
            <div class="wr_bookList_item_info">
                <p :style="{color: globalTheme === 'dark' ? '#FFF' : 'black'}" class="wr_bookList_item_title">{{ book.name }}</p>
                <p class="wr_bookList_item_author">
                    <a>{{ book.author }}</a>
                </p>
                <div style="display: flex">
                    <p class="wr_bookList_item_reading">
                        <span class="wr_bookList_item_readingText wr_bookList_item_readingCountText"><span
                                class="wr_bookList_item_reading_number">{{ book.users }}</span>人评价</span><span
                            class="wr_bookList_item_reading_dep"></span><span class="wr_bookList_item_readingText">
                            {{ book.recommend }}% 推荐<span class="wr_bookList_item_reading_percent"></span></span>
                    </p>
                    <img :src="imgSrc(book)" class="book_rating_item_label_number_image book_rating_item_label_ListItem"
                        style="height: undefinedpx" />
                </div>

                <p class="wr_bookList_item_desc">
                    {{ book.description }}
                </p>
            </div>
        </div>
    </div>
</template>



<style scoped>
.book-list {
    display: flex;
    flex-direction: column;
    gap: 20px;
    width: 100%;
    flex-grow: 1;
    overflow: auto;
}

.index {
    font-size: 1.5rem;
    font-weight: bold;
    flex-shrink: 0;
    width: 50px;
    font-size: 20px;
    font-family:
        DIN-Medium,
        PingFang SC,
        -apple-system,
        SF UI Text,
        Lucida Grande,
        STheiti,
        Microsoft YaHei,
        sans-serif;
    text-align: center;
    color: #212832;
}

.book-item {
    display: flex;
    gap: 15px;
    align-items: center;
}

.book-list :hover {
    background-color: #e3f3ff;
    border-radius: 10px;
    /* 设置鼠标移入样式为手型 */
    cursor: pointer;
}

.book-cover {
    width: 80px;
    height: 120px;
    flex-shrink: 0;
    width: 108px !important;
    height: 156px !important;
    display: block;
    width: 120px;
    height: 174px;
    box-shadow: 0 2px 16px rgba(0, 0, 0, 0.08);
    background: #d8d8d8;
    position: relative;
}

.book-info {
    display: flex;
    flex-direction: column;
}

.wr_bookList_item_author {
    margin-top: 10px;
    font-size: 14px;
    color: #b2b4b8;
    line-height: 21px;
    overflow: hidden;
    height: 21px;
    display: -webkit-box;
    display: -moz-box;
    text-overflow: ellipsis;
    -webkit-line-clamp: 1;
    -moz-line-clamp: 1;
    line-clamp: 1;
    -webkit-box-orient: vertical;
    -webkit-text-size-adjust: none;
    box-orient: vertical;
    height: auto;
    max-height: 21px;
}

.wr_whiteTheme .wr_bookList_item_author {
    color: #5d646e;
}

.wr_bookList_item_reading {
    display: flex;
    align-items: center;
    margin-top: 8px;
    font-family:
        DIN-Medium,
        PingFang SC,
        -apple-system,
        SF UI Text,
        Lucida Grande,
        STheiti,
        Microsoft YaHei,
        sans-serif;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    word-break: break-all;
    word-wrap: normal;
    color: #8a8c90;
}

.wr_whiteTheme .wr_bookList_item_reading {
    color: #858c96;
}

.wr_bookList_item_desc {
    margin-top: 8px;
    font-size: 14px;
    color: #8a8c90;
    line-height: 22px;
    overflow: hidden;
    height: 44px;
    display: -webkit-box;
    display: -moz-box;
    text-overflow: ellipsis;
    -webkit-line-clamp: 2;
    -moz-line-clamp: 2;
    line-clamp: 2;
    -webkit-box-orient: vertical;
    -webkit-text-size-adjust: none;
    box-orient: vertical;
    height: auto;
    max-height: 44px;
}

.wr_whiteTheme .wr_bookList_item_desc {
    color: #858c96;
}

.wr_bookList_item_reading {
    font-family:
        DIN-Medium,
        PingFang SC,
        -apple-system,
        SF UI Text,
        Lucida Grande,
        STheiti,
        Microsoft YaHei,
        sans-serif;
    white-space: nowrap;
    word-break: break-all;
    word-wrap: normal;
    color: #8a8c90;
}

.wr_bookList_item_readingText {
    font-size: 14px;
}

wr_bookList_item_reading_dep {
    width: 1px;
    height: 12px;
    margin: 0 13px;
    background-color: #8a8c90;
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

.book_rating_item_label_number_image {
    width: auto;
    height: 36px;
}

.wr_bookList_item_reading_dep {
    width: 1px;
    height: 12px;
    margin: 0 13px;
    background-color: #8a8c90;
}

.book_rating_item_label_number_image.book_rating_item_label_ListItem {
    height: 22px;
    margin-top: 5px;
}
</style>
