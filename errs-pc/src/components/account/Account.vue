<script setup lang="ts">
import { inject, provide, ref } from 'vue';
import LoginLightPanle from '../../components/account/login/LoginLightPanle.vue';
import LoginDarkPanle from '../../components/account/login/LoginDarkPanle.vue';

const globalTheme = inject<string>("globalTheme");
const selectTheme = ref(globalTheme);
const clickShow = ref("Login")

</script>


<template>
    <LoginLightPanle v-if="selectTheme === 'light' && clickShow === 'Login'" />
    <LoginDarkPanle v-if="selectTheme === 'dark' && clickShow === 'Login'" />
    
</template>

<style lang="css" scoped></style>