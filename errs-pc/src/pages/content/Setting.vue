<script lang="ts" setup>
import { inject, Ref, ref } from 'vue';
import Account from "./setting/Account.vue";
import LoginLightPanle from '../../components/login/LoginLightPanle.vue';
import LoginDarkPanle from '../../components/login/LoginDarkPanle.vue';
import LightTheme from '../../components/theme_model/LightTheme.vue';
import DarkTheme from '../../components/theme_model/DarkTheme.vue';
import SystemCheckbox from '../../components/checkbox/SystemCheckbox.vue';
import GPUCheckbox from '../../components/checkbox/GPUCheckbox.vue';
import PrivacyCheckbox from '../../components/checkbox/privacy/PrivacyCheckbox.vue';

const globalTheme = inject<string>("globalTheme");
const selectTheme = ref(globalTheme);
const globalShowSetting = inject<Ref<boolean>>('globalShowSetting');

</script>

<template>
  <div :style="{ backgroundColor: selectTheme === 'light' ? '#ddd' : '#262727' }" class="setting_content">
    <div
      :style="{ backgroundColor: selectTheme === 'light' ? '#fff' : '#1a1a1a', boxShadow: selectTheme === 'light' ? ' 0 2px 5px rgba(0, 0, 0, 0.1)' : ' 0 2px 5px #fff' }"
      v-if="globalShowSetting" class="card">
      <Account />
      <!-- 常规设置 -->
      <div>
        <p :style="{ color: selectTheme === 'light' ? 'black' : '#FFF' }" class="title">常规设置</p>
        <!-- 主题切换 -->
        <div>
          <p :style="{ color: selectTheme === 'light' ? '#606266' : '#bfc3cb' }" class="title-h2">主题切换</p>
          <LightTheme v-if="selectTheme === 'light'" />
          <DarkTheme v-else />
        </div>
        <!-- 开机自动启动 -->
        <div style="margin-top: 20px;">
          <SystemCheckbox />
        </div>
        <!-- 启用GPU加速 -->
        <div style="margin-top: 20px;">
          <GPUCheckbox />
        </div>
        <div class="divider"></div>
        <!-- 隐私设置 -->
        <p :style="{ color: selectTheme === 'light' ? 'black' : '#FFF' }" class="title">隐私设置</p>
        <PrivacyCheckbox />
      </div>

      <!--  -->

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
  width: 90%;
  height: 90%;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 15px;
  font-weight: 800;
  font-family: Arial, Helvetica, sans-serif;
}

.title-h2 {
  font-size: 13px;
  font-weight: 400;
  font-family: Arial, Helvetica, sans-serif;
}

.divider {
  height: 1px;
  background-color: #ccc;
  margin: 20px 0;
}
</style>