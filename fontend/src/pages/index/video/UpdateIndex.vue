<script setup lang="ts">

import { ref } from 'vue';
import UpdateInfo from './components/update/UpdateInfo.vue';
import VideoList from './components/update/VideoList.vue';

const showUpdateFrom = ref(false)
const movieId = ref(0)
const updateModelValue = (move_id: number) => {
    movieId.value = move_id;
    showUpdateFrom.value = true;
}

const showUpdateList = (value: boolean) => {
    showUpdateFrom.value = value;
}
</script>

<template>
    <div>
        <VideoList @update:modelValue="updateModelValue" v-if="!showUpdateFrom" />
        <UpdateInfo  @update:modelValue="showUpdateList" :movieId="movieId" v-if="showUpdateFrom" style="transform: scale(0.9);margin-top: -40px;" />
    </div>
</template>

<style scoped></style>
