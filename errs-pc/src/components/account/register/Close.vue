<script setup lang="ts">
import { inject } from 'vue';


const globalTheme = inject<string>("globalTheme");

</script>

<template>
    <button class="button">
        <div class="button-box">
            <span :style="{'--fill': globalTheme === 'light' ? 'black' : '#fff'}" class="button-elem">
                <svg t="1737613807133" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="2616">
                    <path
                        d="M301.226667 210.773333a64 64 0 1 0-90.453334 90.453334L421.461333 512l-210.773333 210.773333a64 64 0 0 0 90.538667 90.453334L512 602.538667l210.773333 210.773333a64 64 0 0 0 90.453334-90.538667L602.538667 512l210.773333-210.773333a64 64 0 0 0-90.538667-90.453334L512 421.461333l-210.773333-210.773333z"
                        p-id="2617"></path>
                </svg>
            </span>
            <span :style="{'--fill': globalTheme === 'light' ? 'black' : '#fff'}" class="button-elem">
                <svg t="1737613807133" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="2616">
                    <path
                        d="M301.226667 210.773333a64 64 0 1 0-90.453334 90.453334L421.461333 512l-210.773333 210.773333a64 64 0 0 0 90.538667 90.453334L512 602.538667l210.773333 210.773333a64 64 0 0 0 90.453334-90.538667L602.538667 512l210.773333-210.773333a64 64 0 0 0-90.538667-90.453334L512 421.461333l-210.773333-210.773333z"
                        p-id="2617"></path>
                </svg>
            </span>
        </div>
    </button>
</template>


<style scoped>
.button {
    display: block;
    position: relative;
    width: 56px;
    height: 56px;
    margin: 0;
    overflow: hidden;
    outline: none;
    background-color: transparent;
    cursor: pointer;
    border: 0;
}

.button:before,
.button:after {
    content: "";
    position: absolute;
    border-radius: 50%;
    inset: 7px;
}

.button:before {
    border: 4px solid #f0eeef;
    transition: opacity 0.4s cubic-bezier(0.77, 0, 0.175, 1) 80ms,
        transform 0.5s cubic-bezier(0.455, 0.03, 0.515, 0.955) 80ms;
}

.button:after {
    border: 4px solid #cc96f0;
    transform: scale(1.3);
    transition: opacity 0.4s cubic-bezier(0.165, 0.84, 0.44, 1),
        transform 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94);
    opacity: 0;
}

.button:hover:before,
.button:focus:before {
    opacity: 0;
    transform: scale(0.7);
    transition: opacity 0.4s cubic-bezier(0.165, 0.84, 0.44, 1),
        transform 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94);
}

.button:hover:after,
.button:focus:after {
    opacity: 1;
    transform: scale(1);
    transition: opacity 0.4s cubic-bezier(0.77, 0, 0.175, 1) 80ms,
        transform 0.5s cubic-bezier(0.455, 0.03, 0.515, 0.955) 80ms;
}

.button-box {
    display: flex;
    position: absolute;
    top: 0;
    left: 0;
}

.button-elem {
    display: block;
    width: 22px;
    height: 22px;
    margin: 17px 19px 5px 15px;
    transform: rotate(180deg);
    fill: var(--fill);
}

.button:hover .button-box,
.button:focus .button-box {
    transition: 0.4s;
    transform: translateX(-53px);
}
</style>