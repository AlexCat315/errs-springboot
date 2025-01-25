<script setup lang="ts">
import { inject, Ref, ref } from 'vue';
import Close from '../Close.vue';

const globalTheme = inject<string>("globalTheme");


const showPassowrdOne = ref(false);
const showPassowrdTwo = ref(false);

const globalVerifyRegisterSetup = inject<Ref<number>>("globalVerifyRegisterSetup");
const password = ref('');
const repeatPassword = ref('');
const errorMessage = ref('');
const isError = ref(false);

const validatePassword = () => {
  if (!password.value || password.value.length < 8) {
    errorMessage.value = '密码长度至少8位';
    isError.value = true;
    return false;
  }
  if (password.value !== repeatPassword.value) {
    errorMessage.value = '两次输入的密码不一致';
    isError.value = true;
    return false;
  }
  errorMessage.value = '';
  isError.value = false;
  return true;
};

// 上一步
const prevStep = () => {
    if (globalVerifyRegisterSetup) {
        globalVerifyRegisterSetup.value -= 1;
    }
};
const globalAccountSelect = inject<Ref<string>>("globalAccountSelect");


const showLogin = () => {
    if (globalAccountSelect) {
        globalAccountSelect.value = "Login";
    }
};
</script>

<template>
    <form :style="{ backgroundColor: globalTheme === 'light' ? '#fff' : '#333' }" class="form">
        <Close @click="showLogin()" style="margin-left: 300px;transform: scale(0.7);border: none;
                                    outline: none;" />
        <p :style="{ color: globalTheme === 'light' ? 'black' : '#fff' }" class="form-title">填写密码</p>
        <div class="input-container">
            <input 
                v-model="password"
                placeholder="密码" 
                :type="showPassowrdOne ? 'text' : 'password'"
                :style="{ borderColor: isError ? '#ff4d4f' : '#e5e7eb' }"
                @input="validatePassword"
            >
            <span @click="showPassowrdOne = !showPassowrdOne">
                <svg v-if="showPassowrdOne" stroke="currentColor" viewBox="0 0 24 24" fill="none"
                    xmlns="http://www.w3.org/2000/svg">
                    <path d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" stroke-width="2" stroke-linejoin="round"
                        stroke-linecap="round"></path>
                    <path
                        d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"
                        stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></path>
                </svg>
                <svg v-if="!showPassowrdOne" t="1737774909147" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="4255" width="1rem" height="1rem">
                    <path
                        d="M800 832a32 32 0 0 0 0-45.248L224.192 210.944a32 32 0 0 0-45.248 45.248l54.848 54.848a505.664 505.664 0 0 0-162.304 186.24L64 512l7.488 14.72c86.016 168.96 255.04 273.28 440.832 273.28 61.696 0 121.6-11.584 177.152-33.28l65.28 65.28a32 32 0 0 0 45.248 0z m-287.68-96C355.2 736 212.416 650.88 135.04 512a438.784 438.784 0 0 1 144.384-155.264l69.376 69.376a184.32 184.32 0 0 0 248.96 248.96l41.6 41.6a427.008 427.008 0 0 1-127.104 19.328z m36.864-109.568a120.32 120.32 0 0 1-151.68-151.68l151.68 151.68zM960 512l-7.488-14.72C866.56 328.96 697.472 224 512.32 224c-54.784 0-108.16 9.088-158.336 26.304l51.2 51.2A428.672 428.672 0 0 1 512.32 288c156.48 0 299.264 85.12 377.216 224a439.424 439.424 0 0 1-129.152 144.704l45.312 45.312a505.088 505.088 0 0 0 146.816-175.36L960 512z m-276.544 67.776l-52.16-52.16a120.32 120.32 0 0 0-134.912-134.912L444.16 340.48a184.32 184.32 0 0 1 239.232 239.232z"
                        fill="#9CA3AF" p-id="4256"></path>
                </svg>
            </span>
        </div>
        <div class="input-container">
            <input 
                v-model="repeatPassword"
                placeholder="重复密码" 
                :type="showPassowrdTwo ? 'text' : 'password'"
                :style="{ borderColor: isError ? '#ff4d4f' : '#e5e7eb' }"
                @input="validatePassword"
            >

            <span @click="showPassowrdTwo = !showPassowrdTwo">
                <svg v-if="showPassowrdTwo" stroke="currentColor" viewBox="0 0 24 24" fill="none"
                    xmlns="http://www.w3.org/2000/svg">
                    <path d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" stroke-width="2" stroke-linejoin="round"
                        stroke-linecap="round"></path>
                    <path
                        d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"
                        stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></path>
                </svg>
                <svg v-if="!showPassowrdTwo" t="1737774909147" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="4255" width="1rem" height="1rem">
                    <path
                        d="M800 832a32 32 0 0 0 0-45.248L224.192 210.944a32 32 0 0 0-45.248 45.248l54.848 54.848a505.664 505.664 0 0 0-162.304 186.24L64 512l7.488 14.72c86.016 168.96 255.04 273.28 440.832 273.28 61.696 0 121.6-11.584 177.152-33.28l65.28 65.28a32 32 0 0 0 45.248 0z m-287.68-96C355.2 736 212.416 650.88 135.04 512a438.784 438.784 0 0 1 144.384-155.264l69.376 69.376a184.32 184.32 0 0 0 248.96 248.96l41.6 41.6a427.008 427.008 0 0 1-127.104 19.328z m36.864-109.568a120.32 120.32 0 0 1-151.68-151.68l151.68 151.68zM960 512l-7.488-14.72C866.56 328.96 697.472 224 512.32 224c-54.784 0-108.16 9.088-158.336 26.304l51.2 51.2A428.672 428.672 0 0 1 512.32 288c156.48 0 299.264 85.12 377.216 224a439.424 439.424 0 0 1-129.152 144.704l45.312 45.312a505.088 505.088 0 0 0 146.816-175.36L960 512z m-276.544 67.776l-52.16-52.16a120.32 120.32 0 0 0-134.912-134.912L444.16 340.48a184.32 184.32 0 0 1 239.232 239.232z"
                        fill="#9CA3AF" p-id="4256"></path>
                </svg>
            </span>
        </div>
        <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
        <button class="submit" type="submit" @click.prevent="validatePassword">
            立即注册
        </button>

        <p class="signup-link">
            信息有误？
            <a style="cursor: pointer;color: #00baf8;" @click="prevStep()">上一步</a>
        </p>
    </form>
</template>
<style scoped>
.form {

    display: block;
    padding: 1rem;
    max-width: 350px;
    border-radius: 0.5rem;
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
}

.form-title {
    font-size: 1.25rem;
    line-height: 1.75rem;
    font-weight: 600;
    text-align: center;
    color: #000;
    margin-top: -35px;
}

.input-container {
    position: relative;
}

.input-container input,
.form button {
    outline: none;
    border: 1px solid #e5e7eb;
    margin: 8px 0;
}

.input-container input {
    background-color: #fff;
    padding: 1rem;
    padding-right: 3rem;
    font-size: 0.875rem;
    line-height: 1.25rem;
    width: 300px;
    border-radius: 0.5rem;
    box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
}

.input-container span {
    display: grid;
    position: absolute;
    top: 0;
    bottom: 0;
    right: 0;
    padding-left: 1rem;
    padding-right: 1rem;
    place-content: center;
}

.input-container span svg {
    color: #9CA3AF;
    width: 1rem;
    height: 1rem;
}

.submit {
    display: block;
    padding-top: 0.75rem;
    padding-bottom: 0.75rem;
    padding-left: 1.25rem;
    padding-right: 1.25rem;
    background-color: #4F46E5;
    color: #ffffff;
    font-size: 0.875rem;
    line-height: 1.25rem;
    font-weight: 500;
    width: 100%;
    border-radius: 0.5rem;
    text-transform: uppercase;
}

.error-message {
    color: #ff4d4f;
    font-size: 0.875rem;
    margin-top: -5px;
    margin-bottom: 10px;
}

.signup-link {
    color: #6B7280;
    font-size: 0.875rem;
    line-height: 1.25rem;
    text-align: center;
}

.signup-link a {
    text-decoration: underline;
}

.close-icon {
    position: absolute;
    right: 15px;
    top: 10px;
    cursor: pointer;
}
</style>
