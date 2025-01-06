<script lang="ts" setup>
import NoLogin from './setting/NoLogin.vue';
import {ref} from 'vue';

const isLogin = ref(false);
</script>

<template>
    <NoLogin v-if="!isLogin"/>
</template>

<style scoped lang="css">

</style>