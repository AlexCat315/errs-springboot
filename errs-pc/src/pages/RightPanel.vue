<template>
    <div class="right-panel">
        <h1>Right Panel</h1>
        <!-- Add your content here -->
    </div>
</template>

<script setup>
// 接收全局状态
const globalSelect = inject("globalSelect");

</script>

<style scoped>
.right-panel {
    padding: 20px;
    background-color: #f5f5f5;
}
</style>