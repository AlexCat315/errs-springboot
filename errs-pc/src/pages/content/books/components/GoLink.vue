<script setup lang="ts">
import { inject, Ref } from "vue";

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
            t="1738922873354"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="14663"
            width="25"
            height="25"
        >
            <path
                d="M512 182.857143c-181.638095 0-329.142857 147.504762-329.142857 329.142857s147.504762 329.142857 329.142857 329.142857 329.142857-147.504762 329.142857-329.142857-147.504762-329.142857-329.142857-329.142857z m37.790476 448.609524V560.761905s-163.352381-4.87619-256 114.590476c43.885714-227.961905 256-284.038095 256-284.038095v-67.047619l182.857143 151.161904-182.857143 156.038096z"
                fill="#ff6d00"
                p-id="14664"
            ></path>
        </svg>
    </button>
</template>

<style scoped>
.btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width:  60px;
    height: 60px;
    border-radius: 10px;
    cursor: pointer;
    border: none;
    background-color: transparent;
    position: relative;
    margin-right: -10px;
}

.btn::after {
    content: "分享";
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
    width: 45px;
    height: 45px;
    transition: 0.2s linear;
}

.icon path {
    transition: 0.2s linear;
}

.btn:hover > .icon {
    transform: scale(1.2);
}

.btn:hover > .icon path {
    fill: #fc607b;
}

.btn:hover::after {
    visibility: visible;
    opacity: 1;
    top: 105%;
}
</style>
