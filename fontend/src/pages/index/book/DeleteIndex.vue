<script setup lang="ts">
import { ref } from 'vue';
import BookList from './components/delete/BookList.vue';
import DeleteCard from './components/delete/DeleteCard.vue';
const isDeleteCard = ref(false)
const showErrorpanle = ref(false);
const errorPanleMsg = ref("");

// 定义 Book 类型
interface Book {
    id: number;
    name: string;
    author: string;
    description: string;
    rating: number;
    users: number;
    img: string;
    introduction: string;
    recommend: number;
}

const bookFrom = ref()
const updateModelValue = (book: Book) => {
    bookFrom.value = book;
    isDeleteCard.value = true;
}

const updateModelValueCancel = (value: boolean) => {
    isDeleteCard.value = value;
}

const deleteSuccess = (value: boolean) => {
    isDeleteCard.value = value;
    bookFrom.value = null;
    showErrorpanle.value = true;
    errorPanleMsg.value = "删除成功";
    setTimeout(() => {
        showErrorpanle.value = false;
    }, 2000);
}


</script>
<template>
    <div class="container">
        <p v-if="showErrorpanle" class="error-msg">{{ errorPanleMsg }}</p>
        <BookList @update:modelValue="updateModelValue" v-if="!isDeleteCard" />
        <DeleteCard @delete:success="deleteSuccess" class="delete-card" :book="bookFrom"
            @update:modelValue="updateModelValueCancel" v-if="isDeleteCard" />
    </div>
</template>

<style scoped>
.container {
    position: relative;
    min-height: 100vh;
}

.delete-card {
    position: absolute;
    top: 30%;
    left: 50%;
    transform: translate(-50%, -50%);
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
</style>
