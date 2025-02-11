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
            t="1739284435104"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="13697"
            width="200"
            height="200"
        >
            <path
                d="M512 0c-282.751 0-512 229.247-512 512s229.247 512 512 512 512-229.247 512-512-229.247-512-512-512zM783.36 523.008c-6.080 19.135-13.695 36.864-22.657 53.504-9.024 16.511-18.88 31.744-29.888 45.823-10.752 14.082-21.633 26.753-32.641 38.081-16.832 17.217-34.175 33.28-52.417 48.001-18.24 14.848-35.713 27.712-52.16 38.4-16.64 10.688-31.617 19.264-45.248 25.471-13.568 6.208-24.193 9.345-31.681 9.345-8.447 0.512-19.137-2.497-32-8.769-13.056-6.397-27.392-14.721-42.816-25.471-15.552-10.688-31.871-23.295-49.089-38.017-17.214-14.656-34.112-30.208-50.879-46.528-10.944-10.494-22.657-22.657-35.201-36.416-12.544-13.889-24.384-28.992-35.583-45.567-11.072-16.576-20.61-34.495-28.545-53.758-7.936-19.264-12.288-39.807-13.248-61.632-0.768-21.76 1.472-42.24 6.976-61.248 5.376-19.071 13.568-36.096 24.514-51.264 10.882-15.040 24.32-27.775 40.194-38.015 15.936-10.176 34.048-17.473 54.142-21.633 9.92-2.050 19.968-2.369 30.144-0.895 9.92 1.472 19.841 3.967 29.504 7.487 9.536 3.648 18.88 8.127 27.582 13.632 8.96 5.376 17.214 11.008 25.217 16.96 18.433 13.824 35.969 30.337 52.801 49.537 13.76-17.152 28.479-32.639 44.032-46.399 13.441-11.393 28.607-21.633 45.504-30.784 17.025-9.28 34.624-13.248 53.121-12.033 25.985 2.174 47.617 8.384 65.024 18.624 17.473 10.241 31.232 23.232 41.537 38.848 10.241 15.808 17.214 33.6 21.056 53.504 3.775 19.904 5.056 40.384 3.775 61.314-1.344 20.863-5.056 40.895-11.072 59.904z"
                fill="#EB4F38"
                p-id="13698"
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
