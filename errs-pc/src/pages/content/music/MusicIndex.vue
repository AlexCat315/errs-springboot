<script lang="ts" setup>
import Card from "./components/Card.vue";
import MusicPlay from "./components/MusicPlay.vue";
</script>

<template>
    <!-- Arranged horizontally, each occupies 50% -->
    <!-- margin 10px -->
    <div style="display: flex; justify-content: space-between; margin: 10px;font-family: Arial, Helvetica, sans-serif;">
        <Card :icon="'https://www.alexcat.it.com/minio-api/public-errs/网易云音乐.png'" :iconName="'网易云音乐'" />
        <Card :icon="'https://www.alexcat.it.com/minio-api/public-errs/QQ音乐.png'" :iconName="'QQ音乐'" />
    </div>
    <MusicPlay />
</template>

<style scoped></style>
