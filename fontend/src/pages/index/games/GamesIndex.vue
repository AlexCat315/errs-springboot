<script lang="ts" setup>
import {inject} from "vue";
import Basic from "./Basic.vue";
import AddInfo from "./AddInfo.vue"
import UpdateInfo from './UpdateInfo.vue'

const globalSelect = inject("globalSelect");

</script>

<template>
    <div>
        <Basic v-if="globalSelect === '3-1'" />
        <AddInfo v-if="globalSelect === '3-2'" />
        <UpdateInfo v-if="globalSelect === '3-3'" />
    </div>
</template>

<style scoped></style>
