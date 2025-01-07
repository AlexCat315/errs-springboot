<script setup>
import {onMounted, inject, ref} from 'vue';
import Setting from './content/Setting.vue';
// 接收全局状态
const globalSelect = inject("globalSelect");
const globalTheme = inject("globalTheme");

const selectMenu = ref(globalSelect)
const selectTheme = ref(globalTheme)
</script>
<template>
    <div :style="{backgroundColor:selectTheme==='light'?'#fff':'#191919'}" class="right-panel">
        <Setting v-if="selectMenu === 7"/>
    </div>
</template>

<style scoped>
.right-panel {
    padding: 20px;
}
</style>