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
            t="1739535408627"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="7208"
            width="200"
            height="200"
        >
            <path
                d="M871.706 604.383a28.798 28.798 0 0 0-0.424-2.848c-10.778-53.757-30.987-96.52-60.068-127.102-32.134-33.794-75.04-51.656-124.078-51.656h-81.33V275.923c0-32.759-7.763-61.089-22.449-81.929-18.111-25.701-45.438-39.286-79.025-39.286-32.289 0-59.422 12.159-80.643 36.14-15.932 18.003-28.464 42.428-39.44 76.865-0.122 0.384-0.237 0.77-0.342 1.158-0.104 0.378-10.524 38.298-26.585 77.728-26.383 64.768-43.714 75-46.197 76.177h-42.242c-2.111 0-4.164 0.241-6.146 0.671a28.941 28.941 0 0 0-6.146-0.671 28.726 28.726 0 0 0-16.068 4.887 114.914 114.914 0 0 0-27.945-3.444c-63.359 0-114.721 51.362-114.721 114.721v304.481c0 63.359 51.363 114.721 114.721 114.721 12.383 0 24.297-1.986 35.47-5.616 2.7 0.835 5.568 1.287 8.542 1.287 2.236 0 4.404-0.279 6.494-0.76 2.09 0.481 4.257 0.76 6.494 0.76h433.431c62.209 0 125.171-30.111 160.402-76.711 25.787-34.109 34.238-74.412 23.931-113.787l-15.636-158.932z m-54.338 237.91c-23.949 31.676-70.974 53.8-114.359 53.8H314.484c8.172-15.777 12.816-33.677 12.816-52.671V538.941c0-21.349-5.851-41.324-16.008-58.443h0.655c35.22 0 65.717-33.439 95.976-105.232 17.999-42.705 29.879-84.74 31.493-90.56 19.211-59.972 39.851-72.277 64.916-72.277 29.032 0 43.753 21.362 43.753 63.494v175.715c0 15.94 12.922 28.861 28.861 28.861h110.191c82.849 0 114.952 70.898 127.27 131.001l15.73 159.878a28.91 28.91 0 0 0 0.954 5.042c6.525 23.035 1.908 45.197-13.723 65.873zM931.512 159.915a14.43 14.43 0 0 0-11.649-9.821l-74.355-10.804-33.253-67.377a14.431 14.431 0 0 0-25.88 0l-33.253 67.377-74.355 10.804a14.43 14.43 0 0 0-7.997 24.614l53.804 52.446-12.701 74.055a14.429 14.429 0 0 0 20.937 15.212l66.505-34.964 66.505 34.964a14.414 14.414 0 0 0 6.714 1.657 14.43 14.43 0 0 0 14.224-16.87l-12.701-74.055 53.804-52.446a14.43 14.43 0 0 0 3.651-14.792z"
                fill="#E75934"
                p-id="7209"
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
    content: "推荐";
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
    fill: #ff9780;
}

.btn:hover::after {
    visibility: visible;
    opacity: 1;
    top: 105%;
}
</style>
