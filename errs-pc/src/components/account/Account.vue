<script setup lang="ts">
import { inject, ref, computed, provide } from 'vue';

// 动态导入组件
import LoginLightPanle from '../../components/account/login/LoginLightPanle.vue';
import LoginDarkPanle from '../../components/account/login/LoginDarkPanle.vue';
// 后续可以继续添加更多组件
import RegisterLightPanle from '../../components/account/register/RegisterLightPanle.vue';
import RegisterDarkPanle from '../../components/account/register/RegisterDarkPanle.vue';
import ForGotLightPanle from './forgot/ForGotLightPanle.vue';
import ForGotDarkPanle from './forgot/ForGotDarkPanle.vue';

const globalTheme = inject<string>("globalTheme");
const selectTheme = ref(globalTheme);
const clickShow = ref("Login");
provide("globalAccountSelect", clickShow);

// 组件映射表
const componentMap = {
    Login: {
        light: LoginLightPanle,
        dark: LoginDarkPanle,
    },
    Register: {
        light: RegisterLightPanle,
        dark: RegisterDarkPanle,
    },
    Forgot: {
        light: ForGotLightPanle,
        dark: ForGotDarkPanle,
    },
};


// 动态获取当前组件
const currentComponent = computed(() => {
    const theme = selectTheme.value;
    const panelType = clickShow.value;
    return componentMap[panelType]?.[theme] || null;
});
</script>

<template>
    <transition name="scale" mode="out-in">
        <component :is="currentComponent" v-if="currentComponent" key="clickShow + selectTheme" />
    </transition>
</template>

<style lang="css" scoped>
.scale-enter-active, .scale-leave-active {
  transition: transform 0.5s, opacity 0.5s;
}

.scale-enter-from {
  transform: scale(0.8);
  opacity: 0;
}

.scale-leave-to {
  transform: scale(1.2);
  opacity: 0;
}


</style>
