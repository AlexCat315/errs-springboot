<script lang="ts" setup>
import { inject, provide, Ref, ref, watch } from 'vue';
import Account from "./setting/Account.vue";
import LoginLightPanle from '../../components/login/LoginLightPanle.vue';
import LoginDarkPanle from '../../components/login/LoginDarkPanle.vue';

const isLogin = ref(false);
const globalTheme = inject<string>("globalTheme");
const selectTheme = ref(globalTheme);
const globalShowSetting = inject<Ref<boolean>>('globalShowSetting');

</script>

<template>
  <div :style="{ backgroundColor: selectTheme === 'light' ? '#fff' : '#262727' }" class="setting_content">
    <div
      :style="{ backgroundColor: selectTheme === 'light' ? '#fff' : '#1a1a1a', boxShadow: selectTheme === 'light' ? ' 0 2px 5px rgba(0, 0, 0, 0.1)' : ' 0 2px 5px #fff' }"
      v-if="globalShowSetting" class="card">
      <Account />
    </div>
    <div v-if="!globalShowSetting">
      <LoginLightPanle v-if="selectTheme === 'light'" />
      <LoginDarkPanle v-if="selectTheme === 'dark'" />
    </div>

  </div>
</template>

<style scoped lang="css">
.setting_content {
  height: 100vh;
  display: flex;
  justify-content: center;
  /* 水平居中 */
  align-items: center;
  /* 垂直居中 */
  background-color: #bbbc;
}

.card {
  background-color: #ddd;
  padding: 20px;
  width: 70%;
  height: 70%;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
</style>