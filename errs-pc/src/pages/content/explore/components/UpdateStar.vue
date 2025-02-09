<style scoped>
.card {
    width: 300px;
    height: fit-content;
    background: rgb(255, 255, 255);
    border-radius: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 20px;
    padding: 30px;
    position: relative;
    box-shadow: 20px 20px 30px rgba(0, 0, 0, 0.068);
}
.card-content {
    width: 100%;
    height: fit-content;
    display: flex;
    flex-direction: column;
    gap: 5px;
}
.card-heading {
    font-size: 20px;
    font-weight: 700;
    color: rgb(27, 27, 27);
}
.card-description {
    font-weight: 100;
    color: rgb(102, 102, 102);
}
.card-button-wrapper {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
}
.card-button {
    width: 50%;
    height: 35px;
    border-radius: 10px;
    border: none;
    cursor: pointer;
    font-weight: 600;
}
.primary {
    background-color: rgb(255, 114, 109);
    color: white;
}
.primary:hover {
    background-color: rgb(255, 73, 66);
}
.secondary {
    background-color: #ddd;
}
.secondary:hover {
    background-color: rgb(197, 197, 197);
}
.exit-button {
    display: flex;
    align-items: center;
    justify-content: center;
    border: none;
    background-color: transparent;
    position: absolute;
    top: 20px;
    right: 20px;
    cursor: pointer;
}
.exit-button:hover svg {
    fill: black;
}
.exit-button svg {
    fill: rgb(175, 175, 175);
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
    z-index: 20;
    left: 50%;
    transform: translateX(-50%);
    margin-top: -150px;
    animation: moveUp 0.4s ease-in-out forwards;
    font-size: 13px;
    border-radius: 8px;
    margin-left: 20px;

}
</style>

<template>
 
    <div class="card">
       <p v-if="showErrorpanle" class="error-msg">修改成功</p>
        <div class="card-content">
            <p style="font-family: Arial, Helvetica, sans-serif;" class="card-heading">更新评分?</p>
            <p
                style="font-family: wenquanyimihei; margin-top: -10px"
                class="card-description"
            >
                此操作不可逆
            </p>
        </div>
        <StarScore
            @update:modelValue="handleRatingChange"
            style="
                transform: scale(0.7);
                margin-left: -170px;
                margin-top: -20px;
            "
        />
        <div class="card-button-wrapper">
            <button @click="update(false)" class="card-button secondary">
                取消
            </button>
            <button @click="updateScore()" class="card-button primary">
                更新
            </button>
        </div>
        <button @click="update(false)" class="exit-button">
            <svg height="20px" viewBox="0 0 384 512">
                <path
                    d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"
                ></path>
            </svg>
        </button>
    </div>
</template>

<script setup lang="ts">
import { inject, Ref, ref } from "vue";
import { update_score } from "../../../../net/book/score";
import StarScore from "./StarScore.vue";
import { debounce } from "lodash";

const currentIndex = inject<Ref<number | null>>("currentIndex");
if (currentIndex === undefined) {
    throw new Error("Function not implemented.");
}
const score = ref(0);

const handleRatingChange = (value: number) => {
    score.value = value * 2;
};
const showErrorpanle = ref(false);

const emit = defineEmits(["update:modelValue"]);

const update = (value: boolean) => {
    emit("update:modelValue", value);
};


const updateScore = debounce(() => {
    update_score(
        currentIndex.value,
        score.value,
        (data: any) => {
            console.log(data);
            showErrorpanle.value = true;
            setTimeout(() => {
                showErrorpanle.value = false;
                update(false);
            }, 2000);
        },
        (message: string) => {
            console.log("failure:", message);
        },
    );
}, 300); // 300ms 防抖

</script>
