<script setup lang="ts"></script>

<template>
    <button class="btn">
        <svg t="1739941748840" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="5156" width="200" height="200">
            <path
                d="M714.9568 903.9872H310.8864c-63.2832 0-114.5856-51.3024-114.5856-114.5856V233.0624h633.2416v556.3392c0 63.2832-51.3024 114.5856-114.5856 114.5856z"
                fill="#FF5F5F" p-id="5157"></path>
            <path
                d="M905.6768 217.7024H726.528V170.496c0-42.9568-34.9696-77.9264-77.9264-77.9264h-271.36c-42.9568 0-77.9264 34.9696-77.9264 77.9264v47.2064H120.1664c-8.4992 0-15.36 6.8608-15.36 15.36s6.8608 15.36 15.36 15.36h60.7744v540.9792c0 71.6288 58.3168 129.9456 129.9456 129.9456h404.0704c71.6288 0 129.9456-58.3168 129.9456-129.9456V248.4224h60.7744c8.4992 0 15.36-6.8608 15.36-15.36s-6.8608-15.36-15.36-15.36zM330.0352 170.496c0-26.0096 21.1968-47.2064 47.2064-47.2064h271.4112c26.0096 0 47.2064 21.1968 47.2064 47.2064v47.2064H330.0352V170.496z m484.1472 618.9056c0 54.7328-44.4928 99.2256-99.2256 99.2256H310.8864c-54.7328 0-99.2256-44.4928-99.2256-99.2256V248.4224h602.5216v540.9792z"
                fill="#424242" p-id="5158"></path>
            <path
                d="M379.3408 734.3104c-8.4992 0-15.36-6.8608-15.36-15.36V402.8928c0-8.4992 6.8608-15.36 15.36-15.36s15.36 6.8608 15.36 15.36v316.0576c0 8.448-6.912 15.36-15.36 15.36zM524.0832 734.3104c-8.4992 0-15.36-6.8608-15.36-15.36V402.8928c0-8.4992 6.8608-15.36 15.36-15.36s15.36 6.8608 15.36 15.36v316.0576c0 8.448-6.912 15.36-15.36 15.36zM670.5152 734.3104c-8.4992 0-15.36-6.8608-15.36-15.36V402.8928c0-8.4992 6.8608-15.36 15.36-15.36s15.36 6.8608 15.36 15.36v316.0576c0 8.448-6.912 15.36-15.36 15.36z"
                fill="#FFFFFF" p-id="5159"></path>
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
