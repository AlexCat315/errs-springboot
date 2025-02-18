<script setup lang="ts">
import { inject } from "vue";

const globalTheme = inject<string>("globalTheme");
if (globalTheme === undefined) {
    throw new Error("Function not implemented.");
}
</script>

<template>
    <button
        :style="{
            color: globalTheme === 'dark' ? '#FFF' : '#515151',
        }"
        class="btn"
    >
        <svg
            t="1739341987321"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="6076"
            width="200"
            height="200"
        >
            <path
                d="M572.8768 88.32l82.8416 167.8336a125.39392 125.39392 0 0 0 94.4128 68.608l185.2416 26.9312c52.224 7.5776 73.0624 71.7312 35.2768 108.5952l-134.0416 130.6624a125.32224 125.32224 0 0 0-36.0448 111.0016l31.6416 184.4736c8.9088 52.0192-45.6704 91.648-92.3648 67.1232l-165.6832-87.0912a125.3888 125.3888 0 0 0-116.6848 0l-165.6832 87.0912c-46.6944 24.576-101.2736-15.104-92.3648-67.1232l31.6416-184.4736a125.51168 125.51168 0 0 0-36.0448-111.0016L60.928 460.2368c-37.7856-36.8128-16.896-100.9664 35.2768-108.544l185.2416-26.9312a125.39392 125.39392 0 0 0 94.4128-68.608l82.8416-167.8336c23.3472-47.3088 90.8288-47.3088 114.176 0z"
                fill="#8C7BFD"
                p-id="6077"
            ></path>
            <path
                d="M515.7888 740.5056c-103.7824 0-188.2112-84.4288-188.2112-188.2112s84.4288-188.2112 188.2112-188.2112 188.2112 84.4288 188.2112 188.2112-84.48 188.2112-188.2112 188.2112z m0-270.4896c-45.3632 0-82.2784 36.9152-82.2784 82.2784s36.9152 82.2784 82.2784 82.2784 82.2784-36.9152 82.2784-82.2784-36.9152-82.2784-82.2784-82.2784z"
                fill="#FFE37B"
                p-id="6078"
            ></path>
        </svg>
    </button>
</template>

<style scoped>
.btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 35px;
    height: 35px;
    border-radius: 10px;
    cursor: pointer;
    border: none;
    background-color: transparent;
    position: relative;
    margin-right: -10px;
}

.btn::after {
    content: "收藏";
    width: fit-content;
    height: fit-content;
    position: absolute;
    font-size: 11px;
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
    fill: #ffba00;
}

.btn:hover::after {
    visibility: visible;
    opacity: 1;
    top: 105%;
}
</style>
