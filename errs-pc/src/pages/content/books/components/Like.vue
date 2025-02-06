<script setup lang="ts">
import { ref, inject, Ref } from "vue";
import { validate_token } from "../../../../net/account/validate_token";
import { show } from "@tauri-apps/api/app";
import { onMounted } from "vue";

const globalTheme = inject<string>("globalTheme");
if (globalTheme === undefined) {
    throw new Error("Function not implemented.");
}

// 检查用户是否登录
// 如果没有登录，显示的就是灰色的收藏按钮
const showLike = ref(false);
const isLogin = ref(false);

onMounted(() => {
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
                    isLogin.value = true;
                }
                console.log("success:", data);
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
});
</script>

<template>
    <button
        v-if="showLike"
        :style="{
            color: globalTheme === 'dark' ? '#FFF' : 'black',
            '--svgColor': globalTheme === 'dark' ? '#FFF' : 'black',
        }"
        class="btn"
    >
        <svg
            t="1738822558812"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="4206"
            width="20.625"
            height="20.625"
        >
            <path
                d="M669.781333 130.752c71.637333-11.093333 138.901333 11.477333 193.344 64.533333 55.317333 53.930667 81.834667 124.992 74.282667 199.530667-7.466667 73.642667-46.549333 146.368-112.32 210.474667-18.346667 17.898667-67.669333 66.218667-138.453333 135.637333-31.829333 31.232-65.706667 64.448-99.84 97.984L553.6 871.466667l-13.184 12.949333a40.554667 40.554667 0 0 1-56.832 0l-114.602667-112.64-24.213333-23.722667a677626.346667 677626.346667 0 0 0-145.856-142.762666C133.141333 541.184 94.08 468.48 86.613333 394.816c-7.552-74.538667 18.944-145.6 74.282667-199.530667 54.442667-53.056 121.706667-75.605333 193.344-64.533333 53.162667 8.213333 107.093333 34.688 157.781333 76.949333 50.709333-42.24 104.618667-68.736 157.781334-76.949333z"
                :fill="globalTheme === 'dark' ? '#FFF' : '#8a8a8a'"
                p-id="4207"
            ></path>
        </svg>
    </button>

    <button
        v-else
        :style="{
            color: globalTheme === 'dark' ? '#FFF' : 'black',
            '--svgColor': globalTheme === 'dark' ? '#FFF' : 'black',
        }"
        class="btn"
    >
        <svg
            t="1738822558812"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="4206"
            width="20.625"
            height="20.625"
        >
            <path
                d="M669.781333 130.752c71.637333-11.093333 138.901333 11.477333 193.344 64.533333 55.317333 53.930667 81.834667 124.992 74.282667 199.530667-7.466667 73.642667-46.549333 146.368-112.32 210.474667-18.346667 17.898667-67.669333 66.218667-138.453333 135.637333-31.829333 31.232-65.706667 64.448-99.84 97.984L553.6 871.466667l-13.184 12.949333a40.554667 40.554667 0 0 1-56.832 0l-114.602667-112.64-24.213333-23.722667a677626.346667 677626.346667 0 0 0-145.856-142.762666C133.141333 541.184 94.08 468.48 86.613333 394.816c-7.552-74.538667 18.944-145.6 74.282667-199.530667 54.442667-53.056 121.706667-75.605333 193.344-64.533333 53.162667 8.213333 107.093333 34.688 157.781333 76.949333 50.709333-42.24 104.618667-68.736 157.781334-76.949333z"
                :fill="globalTheme === 'dark' ? '#FFF' : '#8a8a8a'"
                p-id="4207"
            ></path>
        </svg>
    </button>
</template>

<style scoped>
.btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 50px;
    height: 50px;
    border-radius: 10px;
    cursor: pointer;
    border: none;
    background-color: transparent;
    position: relative;
    margin-top: 7px;
    margin-left: 100px;
}

.btn::after {
    content: "收藏";
    width: fit-content;
    height: fit-content;
    position: absolute;
    font-size: 15px;
    font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
        "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
    opacity: 0;
    visibility: hidden;
    transition: 0.2s linear;
    top: 115%;
}

.icon {
    width: 30px;
    height: 30px;
    transition: 0.2s linear;
}

.icon path {
    transition: 0.2s linear;
}

.btn:hover > .icon {
    transform: scale(1.2);
}

.btn:hover > .icon path {
    fill: #d81e06;
}

.btn:hover::after {
    visibility: visible;
    opacity: 1;
    top: 105%;
}
</style>
