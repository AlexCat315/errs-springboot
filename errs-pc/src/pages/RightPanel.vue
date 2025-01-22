<script setup>
import { inject, ref } from 'vue';
import Setting from './content/Setting.vue';
import Explore from './content/Explore.vue';
import Games from './content/Games.vue';
import Books from './content/Books.vue';
import News from './content/News.vue';
import Videos from './content/Videos.vue';
// 接收全局状态
const globalSelect = inject("globalSelect");
const globalTheme = inject("globalTheme");

const selectMenu = ref(globalSelect)

</script>
<template>
    <div>
        <Explore v-if="selectMenu === 1" />
        <Games v-if="selectMenu === 2" />
        <Books v-if="selectMenu === 3" />
        <News v-if="selectMenu === 4" />
        <Videos v-if="selectMenu === 5" />
        <Setting v-if="selectMenu === 7" />

    </div>
</template>

<style lang="css" scoped></style>