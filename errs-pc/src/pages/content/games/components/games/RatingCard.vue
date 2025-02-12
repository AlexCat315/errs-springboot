<template>
    <div
        :style="{ background: globalTheme === 'dark' ? 'black' : '#fff' }"
        class="survey-card"
    >
        <Cancel
            @click="sendMessage()"
            style="margin-left: 96%; margin-top: -25px"
        />
        <p style="margin-top: -20px" class="title">评分</p>
        <div class="rating">
            <button
                v-for="n in 5"
                :key="n"
                :class="['star-button', { active: score >= n }]"
                @click="setRating(n)"
            >
                ★
            </button>
        </div>

        <div>
            <textarea
                id="gameDescription"
                v-model="addRantingFrom.comment"
                class="textarea-field"
                placeholder="游戏评价"
            ></textarea>
        </div>
        <button @click="submitRating" class="submit-button">提交</button>
    </div>
</template>

<script setup lang="ts">
import { ref, inject } from "vue";
import Cancel from "../games/Cancel.vue";
import { add_ranting_comment } from "../../../../../net/games/insert";

const globalTheme = inject<string>("globalTheme");

if (globalTheme === undefined) {
    throw new Error("Function not implemented.");
}

const props = defineProps<{
    ID: number;
}>();
const score = ref(0);
const setRating = (value: number) => {
    score.value = value;
    addRantingFrom.value.score = value * 2;
};
interface RantingComment {
    gameID: number;
    score: number;
    comment: string;
}
const addRantingFrom = ref<RantingComment>({
    gameID: 0, // 默认值
    score: 0, // 默认值
    comment: "", // 默认值
});

const submitRating = () => {
    addRantingFrom.value.gameID = props.ID;
    add_ranting_comment(
        addRantingFrom.value,
        (data) => {
            emit("updateShowRating", false);
        },
        (message: string) => {
            console.log(message);
        },
    );
};

import { defineEmits } from "vue";

// 定义触发的事件和参数类型
const emit = defineEmits<{
    (event: "updateShowRating", newMessage: boolean): void;
}>();

// 通过点击按钮向父组件传递消息
const sendMessage = () => {
    emit("updateShowRating", false);
};
</script>

<style scoped>
.survey-card {
    border: 1px solid #eee;
    padding: 30px;
    width: 320px;
    border-radius: 12px;
    text-align: center;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
}

.title {
    font-size: 19px;
    margin-bottom: 15px;
    color: #2c3e50;
    font-weight: 800;
    font-family: Arial, Helvetica, sans-serif;
}

.description {
    font-size: 15px;
    color: #7f8c8d;
    margin-bottom: 25px;
    line-height: 1.6;
}

.rating {
    display: flex;
    justify-content: center;
    gap: 8px;
    margin-bottom: 20px;
}

.star-button {
    font-size: 28px;
    background: none;
    border: none;
    cursor: pointer;
    color: #ecf0f1;
    transition: all 0.2s ease-in-out;
    padding: 4px;
}

.star-button:hover {
    transform: scale(1.2);
    color: #f1c40f;
}

.star-button.active {
    color: #e67e22;
    text-shadow: 0 2px 8px rgba(230, 126, 34, 0.4);
}

.form-group.full-width {
    grid-column: 1 / -1;
}
.textarea-field {
    min-height: 100px;
    line-height: 1.5;
    width: 100%;
    border-radius: 8px;
    font-size: 13px;
}
.submit-button {
    margin-top: 25px;
    padding: 12px 24px;
    background-color: #3498db;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-weight: 500;
    transition: all 0.2s ease;
    letter-spacing: 0.5px;
    width: 100%;
}

.submit-button:hover {
    background-color: #2980b9;
    transform: scale(1.05);
    box-shadow: 0 2px 8px rgba(52, 152, 219, 0.3);
}
</style>
