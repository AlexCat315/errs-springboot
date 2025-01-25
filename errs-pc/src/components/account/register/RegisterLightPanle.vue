<script lang="ts" setup>
import CodePanle from "./panle/CodePanle.vue";
import { inject, provide, Ref, ref } from "vue";
import EmailPanle from "./panle/EmailPanle.vue";

const setup = ref(1)
provide("globalVerifyRegisterSetup", setup);
const globalVerifyRegisterSetup = inject<Ref<number>>("globalVerifyRegisterSetup");
console.log(globalVerifyRegisterSetup);

</script>

<template>
  <div>
    <EmailPanle v-if="globalVerifyRegisterSetup === 1" />
    <CodePanle v-if="globalVerifyRegisterSetup === 2" />
  </div>
</template>

<style lang="css" scoped>

</style>
