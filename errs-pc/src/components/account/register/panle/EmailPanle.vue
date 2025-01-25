<script setup lang="ts">
import { inject, ref, Ref } from 'vue';
import Close from '../Close.vue';

const globalTheme = inject<string>("globalTheme");
const globalAccountSelect = inject<Ref<string>>("globalAccountSelect");


const showLogin = () => {
  if (globalAccountSelect) {
    globalAccountSelect.value = "Login";
  }
};
const globalVerifyRegisterSetup = inject<Ref<number>>("globalVerifyRegisterSetup");

const toNextStep = () =>{
  if (globalVerifyRegisterSetup) {
    globalVerifyRegisterSetup.value = 2;
  }
}


</script>

<template>
  <div :style="{ background: globalTheme === 'light' ? '#fff' : '#333' }" class="card">
    <Close @click="showLogin()" class="close-icon" /> <!-- 添加 class -->
    <span :style="{ color: globalTheme === 'light' ? 'black' : '#fff' }" class="card__title">加入我们</span>
    <p :style="{ color: globalTheme === 'light' ? 'black' : '#fff' }" class="card__content">填写邮箱信息用于验证，我们承诺不会将此信息用于其它目的，请放心填写。</p>
    <p :style="{ color: globalTheme === 'light' ? 'black' : '#fff' }" style="margin-top: -10px;color: cornflowerblue;"  class="card__content">当前 {{globalVerifyRegisterSetup}}/3 步</p>

    <div class="card__form">
      <input class="email" placeholder="Your Email" type="text">  
      <button @click="toNextStep()" class="sign-up">下一步</button>
    </div>
  </div>
</template>

<style scoped>
.card {
  position: relative;
  width: 340px;
  height: 370px;
  padding: 0 15px;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  gap: 12px;
  background: #fff;
  border-radius: 20px;
}

/* 新增 Close 组件样式 */
.close-icon {
  position: absolute;
  right: 15px;
  top: 10px;
  cursor: pointer;
}

.card__title {
  font-size: 23px;
  font-weight: 900;
  color: #333;
  margin-top: 30px;
}

.card__content {
  font-size: 13px;
  line-height: 18px;
  color: #333;
}

.card__form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.card__form input {
  margin-top: 10px;
  outline: 0;
  box-shadow: transparent 0px 0px 0px 1px inset;
  padding: 0.6em;
  border-radius: 14px;
  border: 1px solid #333;
  width: 280px;
  height: 28px;
  margin-top: -10px;
}

.card__form button {
  border: 0;
  background: #111;
  color: #fff;
  padding: 0.68em;
  border-radius: 14px;
  font-weight: bold;
  height: 45px;
  margin-top: 10px;
}

.sign-up:hover {
  opacity: 0.8;
}
</style>