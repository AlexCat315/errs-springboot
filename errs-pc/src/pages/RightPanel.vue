<template>
    <div class="right-panel">
        <h1>Right Panel</h1>
        <!-- Add your content here -->
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { getSelectMeunKey } from '../script/selectMenu.ts';
// 不断获取当前选中的菜单项
const selectMeunKey = ref(getSelectMeunKey());
// 定时器
setInterval(() => {
   console.log('rightPanel', selectMeunKey);
}, 1000);

</script>

<style scoped>
.right-panel {
    padding: 20px;
    background-color: #f5f5f5;
}
</style>