<script lang="ts" setup>
import CodePanle from "./panle/CodePanle.vue";
import { inject, provide, Ref, ref } from "vue";
import EmailPanle from "./panle/EmailPanle.vue";
import PassWord from "./panle/PassWord.vue";

const setup = ref(1)
provide("globalVerifyRegisterSetup", setup);
interface RegisterForm {
  email: string;
  code: string;
  password: string;
  password_confirmation: string;
}

const registerFrom = ref<RegisterForm>({
  email: "",
  code: "",
  password: "",
  password_confirmation: "",
});

// 提供响应式数据
provide("globalRegisterFrom", registerFrom);
</script>

<template>
  <div>
    <EmailPanle v-if="setup === 1" />
    <CodePanle v-if="setup === 2" />
    <PassWord v-if="setup === 3" />
  </div>
</template>

<style lang="css" scoped></style>
