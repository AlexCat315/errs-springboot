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

const setup = ref(1);


</script>

<template>
  <div :style="{ background: globalTheme === 'light' ? '#fff' : '#333' }" class="card">
    <Close @click="showLogin()" class="close-icon" /> <!-- 添加 class -->
    <span :style="{ color: globalTheme === 'light' ? 'black' : '#fff' }" class="card__title">加入我们</span>
    <p :style="{ color: globalTheme === 'light' ? 'black' : '#fff' }" class="card__content">填写邮箱信息用于验证，我们承诺不会将此信息用于其它目的，请放心填写。</p>
    <p :style="{ color: globalTheme === 'light' ? 'black' : '#fff' }" style="margin-top: -10px;color: cornflowerblue;"  class="card__content">当前 {{setup}}/3 步</p>

    <div class="card__form">
      <input class="email" placeholder="Your Email" type="text">  
      <button class="sign-up">下一步</button>
      <div v-if="setup == 2" style="display: flex;">
        <button style="width: 130px;background-color: rebeccapurple ;" class="sign-up">
          <div style="display: flex;">
            <svg t="1737707467802" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
              p-id="7467" width="30" height="30">
              <path
                d="M64 512c0 247.039 200.961 448 448 448s448-200.961 448-448S759.039 64 512 64 64 264.961 64 512zM355.135 501.149c0.033-0.127 0.193-0.256 0.256-0.384 1.536-3.616 3.649-7.072 6.592-10.048 0.033-0.031 0.065-0.031 0.096-0.064s0.031-0.064 0.064-0.096l158.912-159.359c12.48-12.511 32.735-12.576 45.248-0.064 12.511 12.48 12.544 32.735 0.064 45.248L430.079 513.055l137.408 135.904c12.576 12.447 12.673 32.672 0.256 45.248-6.239 6.336-14.496 9.504-22.751 9.504-8.128 0-16.258-3.103-22.497-9.248L362.24 535.968C352.768 526.592 350.399 512.767 355.135 501.149z"
                fill="#FFF" p-id="7468"></path>
            </svg>
            <p style="font-size: 14px;margin-top: 7px;margin-left: 13px;">上一步</p>
          </div>
        </button>
        <button style="width: 130px;margin-left: 35px;background-color:cadetblue;" class="sign-up">
          <div style="display: flex;">
            <p style="font-size: 14px;margin-top: 7px;margin-left: 25px;">下一步</p>
            <svg style="margin-left: 15px;" t="1737707866714" class="icon" viewBox="0 0 1024 1024" version="1.1"
              xmlns="http://www.w3.org/2000/svg" p-id="8439" data-spm-anchor-id="a313x.search_index.0.i3.67fd3a813CpHpi"
              width="30" height="30">
              <path
                d="M512 64c-247.039484 0-448 200.960516-448 448S264.960516 960 512 960 960 759.039484 960 512 759.039484 64 512 64zM661.760516 535.967531l-160.25545 158.495686c-6.239161 6.14455-14.368821 9.247789-22.496761 9.247789-8.255235 0-16.512189-3.168606-22.751351-9.504099-12.416396-12.576374-12.320065-32.800753 0.25631-45.248112l137.407768-135.904314-136.287918-136.671523c-12.480043-12.512727-12.447359-32.768069 0.063647-45.248112 12.512727-12.512727 32.768069-12.447359 45.248112 0.063647l158.911974 159.359226c0.032684 0.032684 0.032684 0.063647 0.063647 0.096331s0.063647 0.032684 0.096331 0.063647c2.94326 2.975944 5.055665 6.431824 6.591802 10.047682 0.063647 0.127295 0.223626 0.25631 0.25631 0.383604C673.600645 512.767209 671.231931 526.592447 661.760516 535.967531z"
                fill="#ffffff" p-id="8440"></path>
            </svg>
          </div>
        </button>

      </div>
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