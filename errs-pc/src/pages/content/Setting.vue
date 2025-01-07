<script lang="ts" setup>
import { ref} from 'vue';
import Account from "./setting/Account.vue";

const isLogin = ref(false);
</script>

<template>
    <div class="setting_content">
        <Account />
    </div>
</template>

<style scoped lang="css">
.setting_content {
    height: 100vh;
    font-family: 'yousu-title-black', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
</style>