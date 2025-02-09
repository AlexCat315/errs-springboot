<script setup lang="ts">
import {inject} from "vue";

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
            t="1739071317766"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="12496"
            width="11"
            height="11"
        >
            <path
                d="M544.568 463.452L144.384 46.438a64.655 64.655 0 0 0-94.141 0 71.5 71.5 0 0 0 0 98.187l353.024 367.943L50.243 880.415a71.613 71.613 0 0 0 0 98.196 64.681 64.681 0 0 0 94.141 0l400.18-416.952a71.5 71.5 0 0 0 0.004-98.207z m434.34 0L578.73 46.438a64.655 64.655 0 0 0-94.147 0 71.5 71.5 0 0 0 0 98.187l353.024 367.943-353.024 367.847a71.613 71.613 0 0 0 0 98.196 64.681 64.681 0 0 0 94.147 0l400.18-416.952a71.5 71.5 0 0 0 0-98.207z"
                :fill="globalTheme === 'dark' ? '#FFF' : '#8a8a8a'"
                p-id="12497"
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
    margin-top: 10%;
}

.btn::after {
    content: "查看全部";
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
