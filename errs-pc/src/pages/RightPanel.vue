<script setup>
import { inject, ref, computed, provide } from "vue";
import Setting from "./content/Setting.vue";
import Explore from "./content/Explore.vue";
import Games from "./content/Games.vue";
import Books from "./content/Books.vue";
import Videos from "./content/Videos.vue";
import Music from "./content/Music.vue";
import Search from "./content/Search.vue";

// 接收全局状态
const globalSelect = inject("globalSelect");

const selectMenu = ref(globalSelect);

const componentMap = {
    1: Explore,
    2: Games,
    3: Books,
    4: Music,
    5: Videos,
    6: Search,
    7: Setting,
};

// 动态选择组件
const currentComponent = computed(() => componentMap[selectMenu.value]);

const currentIndex = ref(null);

provide("currentIndex", currentIndex);
</script>

<template>
    <transition name="fade-scale" mode="out-in">
        <component :is="currentComponent" :key="selectMenu" />
    </transition>
</template>

<style lang="css" scoped>
.fade-scale-enter-active,
.fade-scale-leave-active {
    transition: all 0.5s ease;
}

.fade-scale-enter-from {
    transform: scale(0.9);
    opacity: 0;
}

.fade-scale-leave-to {
    transform: scale(1.2);
    opacity: 0;
}
</style>
