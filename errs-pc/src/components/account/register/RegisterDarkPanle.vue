<script lang="ts" setup>

import { inject, provide, Ref, ref } from "vue";
import CodePanle from "./panle/CodePanle.vue";
import EmailPanle from "./panle/EmailPanle.vue";

const setup = ref(1)
provide("globalVerifyRegisterSetup", setup);


</script>

<template>
  <div>
    <EmailPanle v-if="setup === 1" />
    <CodePanle v-if="setup === 2" />
  </div>
</template>

<style lang="css" scoped>

</style>
