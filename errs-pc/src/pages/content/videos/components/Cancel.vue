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
            t="1739331343190"
            class="icon"
            viewBox="0 0 1140 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="4199"
            width="200"
            height="200"
        >
            <path
                d="M474.133828 76.681372c-261.931418 0-474.133828 212.297312-474.133828 474.133828 0 261.836515 212.20241 474.133828 474.133828 474.133828s474.133828-212.297312 474.133828-474.133828C948.267655 288.978684 735.970343 76.681372 474.133828 76.681372zM521.774977 637.271548 521.774977 521.774977c-57.321223 0-203.471362 1.328638-203.471362 158.487488 0 82.28063 55.80278 150.990176 130.016682 166.838925C329.217424 830.208712 237.066914 724.487118 237.066914 595.134754c0-240.293605 245.228545-242.286562 284.708063-242.286562L521.774977 254.529008l189.330862 192.08304L521.774977 637.271548z"
                p-id="4200"
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
    content: "取消";
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
    fill: #1296db;
}

.btn:hover::after {
    visibility: visible;
    opacity: 1;
    top: 105%;
}
</style>
