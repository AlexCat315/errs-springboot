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
            t="1738825941798"
            class="icon"
            viewBox="0 0 1025 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="5419"
            width="11"
            height="11"
        >
            <path
                d="M1020.85564 385.279248c-8.427984-26.487948-30.099941-45.751911-57.189888-49.965902l-83.075838-12.641975-171.569665-29.497942c-10.23398-1.805996-19.263962-8.427984-24.079953-18.059965l0-0.601999c-0.601999-2.407995-0.601999-4.213992-1.805996-6.621987L575.978509 41.537919c-3.009994-6.621987-7.223986-12.641975-11.437978-17.457966l0 0 0 0C550.694558 9.029982 532.634593 0 512.768632 0c-27.089947 0-51.1699 15.651969-63.209877 40.93592L329.158991 294.979424 60.667515 335.915344c-27.089947 4.213992-48.761905 22.875955-57.189888 49.965902-8.427984 27.089947-1.203998 55.985891 18.059965 75.249853l194.44562 197.455614-45.751911 278.725456c-4.815991 27.691946 6.019988 55.383892 28.293945 71.63786 21.671958 16.855967 50.567901 18.661964 74.045855 5.417989l240.197531-131.837743 240.197531 131.837743c10.23398 6.019988 21.671958 8.427984 32.507937 8.427984 14.447972 0 28.895944-4.815991 41.537919-14.447972 21.671958-16.855967 32.507937-43.945914 28.293945-71.63786l-45.751911-278.725456 194.44562-197.455614C1022.661636 441.265138 1029.283623 412.369195 1020.85564 385.279248z"
                fill="#ffba00"
                p-id="5420"
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
    margin-top: -3px;
    margin-right: -10px;
}

.btn::after {
    content: "取消收藏";
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
    fill: #8a8a8a;
}

.btn:hover::after {
    visibility: visible;
    opacity: 1;
    top: 105%;
}
</style>
