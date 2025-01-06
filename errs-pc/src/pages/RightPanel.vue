
<script setup>
import { inject,ref } from 'vue';
import Setting from './content/Setting.vue';
// 接收全局状态
const globalSelect = inject("globalSelect");
const selectIndex = ref(globalSelect.value);
</script>
<template>
    <div class="right-panel">
     <div>
        <Setting v-if="selectIndex === 7"/>
     </div>
    </div>
</template>

<style scoped>
.right-panel {
    padding: 20px;
    background-color: #f5f5f5;
}
</style>