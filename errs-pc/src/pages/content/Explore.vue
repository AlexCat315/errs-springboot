<script setup lang="ts">
import { inject, ref } from 'vue';
import WeatherCard from './explore/WeatherCard.vue';


const globalTheme = inject<string>("globalTheme");
const selectTheme = ref(globalTheme);
</script>
<template>
    <div>
        <WeatherCard />
    </div>
</template>


<style scoped></style>