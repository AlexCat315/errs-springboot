<script setup lang="ts">
import { ref } from 'vue';
import BookList from './components/update/BookList.vue';
import UpdateCard from './components/update/EditCard.vue';
const isDeleteCard = ref(false)
const showErrorpanle = ref(false);
const errorPanleMsg = ref("");

// Define Book Type
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

const bookFrom = ref<Book | null>(null); // Initialize as null

const updateModelValue = (book: Book) => {
    bookFrom.value = book;
    isDeleteCard.value = true;
}

const updateModelValueCancel = () => {  // Simplified cancel handler
    isDeleteCard.value = false;
     bookFrom.value = null; // Reset bookFrom
}

const deleteSuccess = (value: boolean) => { // value is always false, not used, no need to change isDeleteCard.value
    // isDeleteCard.value = value; // no need 
    bookFrom.value = null;
    showErrorpanle.value = true;
    errorPanleMsg.value = "删除成功";  // Better message
    setTimeout(() => {
        showErrorpanle.value = false;
    }, 2000);
}

</script>

<template>
    <div class="container">
        <p v-if="showErrorpanle" class="error-msg">{{ errorPanleMsg }}</p>
        <BookList @update:modelValue="updateModelValue" v-if="!isDeleteCard" />
         <!--  Changed event name to 'cancel' -->
        <UpdateCard  @delete:success="deleteSuccess" class="delete-card" :book="bookFrom"
            @cancel="updateModelValueCancel" v-if="isDeleteCard" />
    </div>
</template>

<style scoped>
.container {
    position: relative;
    min-height: 100vh;
}

.delete-card {
    position: absolute;

    left: 20%;
    margin-top: -80px;
    height: 100vh;
    transform: scale(0.8);
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
