<script lang="ts" setup>
import { ref} from 'vue';
import Account from "./setting/Account.vue";

const isLogin = ref(false);
</script>

<template>
    <div class="setting_content">
        <div class="card">
            <Account />
        </div>
    </div>
</template>

<style scoped lang="css">
.setting_content {
  height: 100vh;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center;     /* 垂直居中 */
  background-color: #bbbc ;
}

.card {
  background-color: #ddd;
  padding: 20px;
  width: 70%;
  height: 70%;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}
</style>