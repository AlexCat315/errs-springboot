<script setup lang="ts"></script>

<template>
    <button class="btn">
        <svg
            t="1739443760864"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="5149"
            width="200"
            height="200"
        >
            <path
                d="M510.1 475.9L378.4 339.5c-10.4-10.7-27.4-10.9-38.1-0.5-5.2 4.9-8.2 11.7-8.3 18.9-0.2 7.2 2.6 14.1 7.5 19.2l131.7 136.4-136.4 131.7c-10.7 10.4-10.9 27.4-0.5 38.1 4.9 5.2 11.7 8.2 18.9 8.3 7.2 0.2 14.1-2.6 19.2-7.5l136.4-131.7 131.7 136.4c10.4 10.7 27.4 10.9 38.1 0.5 5.2-4.9 8.2-11.7 8.3-18.9 0.2-7.2-2.6-14.1-7.5-19.2L547.7 514.8l136.4-131.7c10.7-10.4 10.9-27.4 0.5-38.1-4.9-5.2-11.7-8.2-18.9-8.3-7.2-0.2-14.1 2.6-19.2 7.5L510.1 475.9zM220.4 793.2c-155.7-161.3-151.2-418.2 10-574 161.3-155.7 418.2-151.2 574 10 155.7 161.3 151.3 418.2-10 574-161.3 155.7-418.2 151.3-574-10z m0 0"
                fill="#333333"
                p-id="5150"
            ></path>
        </svg>
    </button>
</template>

<style scoped>
.btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40px;
    height: 40px;
    border-radius: 10px;
    cursor: pointer;
    border: none;
    background-color: transparent;
    position: relative;
    margin-right: -10px;
}

.btn::after {
    content: "关闭";
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
    fill: red;
}

.btn:hover::after {
    visibility: visible;
    opacity: 1;
    top: 105%;
}
</style>
