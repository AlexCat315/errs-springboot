<script setup lang="ts"></script>

<template>
    <button class="btn">
        <svg
            t="1739428344030"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="4182"
            width="200"
            height="200"
        >
            <path
                d="M714.112 464H424.512l121.728-121.792a48.64 48.64 0 0 0 0.96-68.736 48.64 48.64 0 0 0-68.8 0.832L275.84 476.928c-0.448 0.384-1.024 0.512-1.472 0.96a47.36 47.36 0 0 0-13.696 34.624c0 12.224 4.48 24.448 13.76 33.664 0.384 0.384 0.896 0.512 1.28 0.832l202.688 202.688c19.264 19.2 50.048 19.712 68.8 0.96a48.64 48.64 0 0 0-0.96-68.736L424.448 560h289.664c27.2 0 49.28-21.504 49.28-48s-22.08-48-49.28-48z"
                fill="#FFF"
                p-id="4183"
            ></path>
            <path
                d="M512 32A479.936 479.936 0 0 0 32 512c0 265.152 214.848 480 480 480s480-214.848 480-480S777.152 32 512 32zM512 896A384 384 0 1 1 512 128a384 384 0 0 1 0 768z"
                fill="#FFF"
                p-id="4184"
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
    margin-right: -10px;
}

.btn::after {
    color: #fff;
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
    width: 70px;
    height: 70px;
    transition: 0.2s linear;
}

.icon path {
    transition: 0.2s linear;
}

.btn:hover > .icon {
    transform: scale(1.2);
}

.btn:hover > .icon path {
    fill: green;
}

.btn:hover::after {
    visibility: visible;
    opacity: 1;
    top: 105%;
}
</style>
