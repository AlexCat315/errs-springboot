<script lang="ts" setup>
import { watch } from "vue";
import { ref, inject } from "vue";

const searchText = ref("");

watch(searchText, (newValue, oldValue) => {
    if (newValue !== oldValue) {
        // 300ms 后调用一次searchTextClick 用来消除防抖
        setTimeout(searchTextClick, 300);
    }
});

const searchTextClick = () => {
    if (searchText.value !== "") {
        console.log("searchText", searchText.value);
    }
};

const globalSelect = inject<{ value: string }>("globalSelect");
function handleClick(event: any) {
     globalSelect!.value = "2-5";
    event.stopPropagation(); // 阻止事件传播
}
</script>

<style scoped>

.form button {
    border: none;
    background: none;
    color: #8b8ba7;
}
.form {
    --timing: 0.3s;
    --width-of-input: 300px;
    --height-of-input: 40px;
    --border-height: 2px;
    --input-bg: #fff;
    --border-color: #2f2ee9;
    --border-radius: 30px;
    --after-border-radius: 1px;
    position: relative;
    width: var(--width-of-input);
    height: var(--height-of-input);
    display: flex;
    align-items: center;
    padding-inline: 0.8em;
    border-radius: var(--border-radius);
    transition: border-radius 0.5s ease;
    background: var(--input-bg, #fff);
}
/* styling of Input */
.input {
    font-size: 0.9rem;
    background-color: transparent;
    width: 100%;
    height: 100%;
    padding-inline: 0.5em;
    padding-block: 0.7em;
    border: none;
}
/* styling of animated border */
.form:before {
    content: "";
    position: absolute;
    background: var(--border-color);
    transform: scaleX(0);
    transform-origin: center;
    width: 100%;
    height: var(--border-height);
    left: 0;
    bottom: 0;
    border-radius: 1px;
    transition: transform var(--timing) ease;
}
/* Hover on Input */
.form:focus-within {
    border-radius: var(--after-border-radius);
}

input:focus {
    outline: none;
}
/* here is code of animated border */
.form:focus-within:before {
    transform: scale(1);
}
/* styling of close button */
/* == you can click the close button to remove text == */
.reset {
    border: none;
    background: none;
    opacity: 0;
    visibility: hidden;
}
/* close button shown when typing */
input:not(:placeholder-shown) ~ .reset {
    opacity: 1;
    visibility: visible;
}
/* sizing svg icons */
.form svg {
    width: 17px;
    margin-top: 3px;
}
</style>

<template>
    <div class="form">
        <button disabled>
            <svg
                width="17"
                height="16"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
                role="img"
                aria-labelledby="search"
            >
                <path
                    d="M7.667 12.667A5.333 5.333 0 107.667 2a5.333 5.333 0 000 10.667zM14.334 14l-2.9-2.9"
                    stroke="currentColor"
                    stroke-width="1.333"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                ></path>
            </svg>
        </button>
        <input
            class="input"
            placeholder="智能搜索，一键触达"
            v-model="searchText"
            type="text"
            @click="handleClick"
        />
    </div>
</template>
