<script lang="ts" setup>
import { onMounted, ref, inject, Ref, watch } from "vue";
import { get_book_top250_info } from "../../../../net/book/get_book";
import { get_book_search } from "../../../../net/book/get_book";

const globalTheme = inject<string>("globalTheme");
if (globalTheme === undefined) {
    throw new Error("Function not implemented.");
}
const globalSearch = inject("globalSearch") as Ref<string>;
if (!globalSearch) {
    throw new Error("globalSearch is not provided");
}

const currentIndex = inject<Ref<number>>("currentIndex");
if (currentIndex === undefined) {
    throw new Error("currentIndex is undefined");
}
const goDetail = (id: number) => {
    currentIndex.value = id;
};

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
const currentStart = ref(1);
const loading = ref(false);

const mergeBooks = (newBooks: Book[]) => {
    const existingIds = new Set(books.value.map((book) => book.id));
    const uniqueBooks = newBooks.filter((book) => !existingIds.has(book.id));
    books.value = [...books.value, ...uniqueBooks];
};

const fetchBook = (page: number) => {
    if (loading.value) return;
    loading.value = true;

    get_book_search(
        page,
        10,
        globalSearch.value,
        (response: any) => {
            if (response && Array.isArray(response.data)) {
                mergeBooks(response.data);
                currentStart.value = page + response.data.length;
                loading.value = false;
            } else {
                console.error("返回的数据格式错误", response);
                loading.value = false;
            }
        },
        (errorMessage: string) => {
            console.error(errorMessage);
            loading.value = false;
        }
    );
};

onMounted(() => {
    books.value = [];
    fetchBook(1);
});

watch(() => globalSearch.value, () => {
    books.value = [];
    if (globalSearch.value !== '') {
        fetchBook(1);
    }
});

const scrollContainer = ref(null);

const handleScroll = () => {
    if (!scrollContainer.value) return;
    const { scrollTop, scrollHeight, clientHeight } = scrollContainer.value;
    const isBottom = scrollTop + clientHeight >= scrollHeight - 5;

    if (isBottom && !loading.value) {
        fetchBook(++currentStart.value);
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
        <div @click="goDetail(book.id)" v-for="(book, index) in books" :key="index" class="book-item">
            <p class="index">{{ index + 1 }}</p>
            <img :src="book.img" alt="Book cover" class="book-cover" />
            <div class="wr_bookList_item_info">
                <p :style="{ color: globalTheme === 'dark' ? '#FFF' : 'black' }" class="wr_bookList_item_title">{{
                    book.name }}</p>
                <p class="wr_bookList_item_author">
                    <a>{{ book.author }}</a>
                </p>
                <div class="rating-container">
                    <p class="wr_bookList_item_reading">
                        <span class="wr_bookList_item_readingCountText">
                            <span class="wr_bookList_item_reading_number">{{ book.users }}</span>人评价
                        </span>
                        <span class="wr_bookList_item_reading_dep"></span>
                        <span class="wr_bookList_item_readingText">
                            {{ book.recommend }}% 推荐
                        </span>
                    </p>
                    <img :src="imgSrc(book)" class="book_rating_item_label_ListItem" />
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
    height: calc(100vh - 160px);
    padding: 0 20px;
    overflow-y: auto;
    scrollbar-width: none;
    background: #fff;
    border-radius: 20px;
    box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
    margin: 10px 0 80px;
}

.book-list::-webkit-scrollbar {
    display: none;
}

.book-item {
    display: flex;
    gap: 15px;
    padding: 15px;
    transition: background-color 0.2s;
}

.book-item:hover {
    background-color: #e3f3ff;
    border-radius: 10px;
    cursor: pointer;
}

.index {
    min-width: 40px;
    font-size: 20px;
    font-family: DIN-Medium, PingFang SC, -apple-system, sans-serif;
    text-align: center;
    color: #212832;
}

.book-cover {
    width: 108px;
    height: 156px;
    flex-shrink: 0;
    object-fit: cover;
    box-shadow: 0 2px 16px rgba(0, 0, 0, 0.08);
}

.wr_bookList_item_info {
    flex: 1;
    min-width: 0;
}

.wr_bookList_item_title {
    margin: 0;
    font-size: 16px;
    line-height: 1.4;
    font-family: Arial, Helvetica, sans-serif;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.wr_bookList_item_author {
    margin-top: 10px;
    font-size: 14px;
    color: #b2b4b8;
    line-height: 21px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 1;
    -webkit-box-orient: vertical;
}

.rating-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 8px;
}

.wr_bookList_item_reading {
    display: flex;
    align-items: center;
    gap: 8px;
    font-family: DIN-Medium, PingFang SC, -apple-system, sans-serif;
    color: #8a8c90;
    font-size: 14px;
}

.wr_bookList_item_reading_dep {
    width: 1px;
    height: 12px;
    background-color: #8a8c90;
}

.book_rating_item_label_ListItem {
    height: 22px;
    margin-left: 8px;
}

.wr_bookList_item_desc {
    margin-top: 8px;
    font-size: 14px;
    color: #8a8c90;
    line-height: 22px;
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
}

@media (max-width: 600px) {
    .book-item {
        padding: 10px;
        gap: 10px;
    }

    .book-cover {
        width: 80px;
        height: 120px;
    }

    .index {
        min-width: 30px;
        font-size: 16px;
    }
}
</style>
