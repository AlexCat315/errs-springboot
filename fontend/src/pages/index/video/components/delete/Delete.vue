<script setup lang="ts"></script>

<template>
    <button class="btn">
        <svg t="1739933558057" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="5230" width="200" height="200">
            <path
                d="M926.72 102.4h-153.6V51.2c0-28.16-23.04-51.2-51.2-51.2H312.32c-28.16 0-51.2 23.04-51.2 51.2v51.2H107.52c-28.16 0-51.2 23.04-51.2 51.2v35.84c0 8.704 6.656 15.36 15.36 15.36h890.88c8.704 0 15.36-6.656 15.36-15.36v-35.84c0-28.16-23.04-51.2-51.2-51.2zM107.52 870.4c0 84.992 68.608 153.6 153.6 153.6h512c84.992 0 153.6-68.608 153.6-153.6V256H107.52v614.4z m577.536-343.04c0-16.896 13.824-30.72 30.72-30.72s30.72 13.824 30.72 30.72v225.28c0 16.896-13.824 30.72-30.72 30.72s-30.72-13.824-30.72-30.72v-225.28zM486.4 506.88c0-16.896 13.824-30.72 30.72-30.72s30.72 13.824 30.72 30.72v266.24c0 16.896-13.824 30.72-30.72 30.72s-30.72-13.824-30.72-30.72V506.88z m-198.656 20.48c0-16.896 13.824-30.72 30.72-30.72s30.72 13.824 30.72 30.72v225.28c0 16.896-13.824 30.72-30.72 30.72s-30.72-13.824-30.72-30.72v-225.28z"
                fill="#DD6572" p-id="5231"></path>
        </svg>
    </button>
</template>

<style scoped>
.btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 30px;
    height: 30px;
    border-radius: 10px;
    cursor: pointer;
    border: none;
    background-color: transparent;
    position: relative;
    margin-right: -10px;
}

.btn::after {
    content: "删除";
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
    width: 70px;
    height: 70px;
    transition: 0.2s linear;
}

.icon path {
    transition: 0.2s linear;
}

.btn:hover>.icon {
    transform: scale(1.2);
}

.btn:hover>.icon path {
    fill: red;
}

.btn:hover::after {
    visibility: visible;
    opacity: 1;
    top: 105%;
}
</style>
