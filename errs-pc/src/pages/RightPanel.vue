<script setup>
import {inject, ref} from 'vue';
import Setting from './content/Setting.vue';
import Explore from './content/Explore.vue';
// 接收全局状态
const globalSelect = inject("globalSelect");
const globalTheme = inject("globalTheme");

const selectMenu = ref(globalSelect)

</script>
<template>
    <div>
        <Explore v-if="selectMenu === 1"/>
        <Setting v-if="selectMenu === 7"/>
    </div>
</template>

<style lang="css" scoped>


</style>