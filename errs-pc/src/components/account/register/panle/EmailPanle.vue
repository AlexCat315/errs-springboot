<script setup lang="ts">
import { inject, ref, Ref } from 'vue';
import Close from '../Close.vue';
import { validate_code, validate_email } from '../../../../net/account/register';
import Loading from '../../../Loading.vue';


const globalTheme = inject<string>("globalTheme");
const globalAccountSelect = inject<Ref<string>>("globalAccountSelect");

interface RegisterForm {
  email: string;
  code: string;
  password: string;
  password_confirmation: string;
}

// 注入父组件提供的数据
const registerFrom = inject<Ref<RegisterForm>>("globalRegisterFrom");

// 安全校验（确保注入成功）
if (!registerFrom) {
  throw new Error("未能注入 globalRegisterFrom");
}

const showLogin = () => {
  if (globalAccountSelect) {
    globalAccountSelect.value = "Login";
  }
};
const globalVerifyRegisterSetup = inject<Ref<number>>("globalVerifyRegisterSetup");

const toNextStep = () => {
  if (globalVerifyRegisterSetup) {
    globalVerifyRegisterSetup.value = 2;
  }
};



const email = ref("");
const emailError = ref(false);
const errorMessage = ref("");
const showLoading = ref(false);
const showErrorpanle = ref(false);
const errorPanleMsg = ref("");


const verifyEmail = () => {
  emailError.value = false;
  errorMessage.value = "";

  if (!email.value) {
    emailError.value = true;
    errorMessage.value = "邮箱不能为空";
    return;
  };

  // 验证邮箱格式
  if (!email.value.match(/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/)) {
    emailError.value = true;
    errorMessage.value = "邮箱格式不正确";
    return;
  };
  showLoading.value = true;

  // 验证邮箱逻辑
  validate_email(email.value, (data: any) => {
    if (data.code === 200) {
      registerFrom.value.email = email.value;
      toNextStep();
    }
    showLoading.value = false;
    return;
  }, (message: string) => {
    showLoading.value = false;
    emailError.value = true;
    errorMessage.value = message;
    return;
  }, () => {
    showLoading.value = false;
    emailError.value = true;
    errorPanleMsg.value = "网络错误，请稍后重试";
    showErrorpanle.value = true;
    setTimeout(() => {
      showErrorpanle.value = false;
    }, 3000);
    return;
  });

  validate_code(email.value,"", ()=>{

  },()=>{

  });
};


</script>

<template>

  <!-- Loading 遮罩层 -->
  <div v-if="showLoading" class="loading-overlay">
    <Loading style="margin-left: 200px; margin-top: -120px" />
  </div>

  <p v-if="showErrorpanle" class="error-msg">{{ errorPanleMsg }}</p>


  <div :style="{ background: globalTheme === 'light' ? '#fff' : '#333' }" class="card">
    <Close @click="showLogin()" style="transform: scale(0.8);" class="close-icon" />
    <span :style="{ color: globalTheme === 'light' ? 'black' : '#fff' }" class="card__title">加入我们</span>
    <p :style="{ color: globalTheme === 'light' ? 'black' : '#fff' }" class="card__content">
      填写邮箱信息用于验证，我们承诺不会将此信息用于其它目的，请放心填写。</p>
    <p :style="{ color: globalTheme === 'light' ? 'black' : '#fff' }" style="margin-top: -10px;color: cornflowerblue;"
      class="card__content">当前 {{ globalVerifyRegisterSetup }}/3 步</p>

    <div class="card__form">
      <input class="email" :class="{ error: emailError }" v-model="email" @input="emailError = false" placeholder="邮箱账号"
        type="text">
      <div v-if="emailError" class="error-message">{{ errorMessage }}</div>
      <button @click="verifyEmail()" style="background-color: #00ad54;border-radius: 13px ;"
        class="sign-up">下一步</button>
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
  font-weight: bold;
  height: 45px;
  margin-top: 10px;
}


.sign-up:hover {
  opacity: 0.8;
  background-color: #00ad54;

}

.email.error {
  border-color: #ff4d4f;
}

.error-message {
  color: #ff4d4f;
  font-size: 12px;
  margin-top: 4px;
  text-align: left;
  padding-left: 8px;
}

.loading-overlay {
  position: fixed;
  top: 0;
  right: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(236, 236, 236, 0.8);
  /* 半透明背景 */
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2;
  /* 确保遮罩层在最上层 */
}

@keyframes moveUp {
  0% {
    transform: translateY(0);
  }

  100% {
    transform: translateY(-20px);
  }
}

.error-msg {
  background-color: rgba(0, 0, 0, 0.7);
  color: #FFF;
  width: 150px;
  height: 30px;
  /* 建议显式设置高度 */
  display: flex;
  /* 启用 Flex 布局 */
  align-items: center;
  /* 垂直居中 */
  justify-content: center;
  /* 水平居中 */
  font-family: Arial, Helvetica, sans-serif;
  position: fixed;
  z-index: 4;
  margin-left: 100px;
  margin-top: 30px;
  animation: moveUp 0.4s ease-in-out forwards;
  font-size: 13px;
  border-radius: 8px;
}
</style>
