<script setup lang="ts">
import { inject, Ref, ref } from "vue";
import {
    send_code,
    forgot_password,
} from "../../../net/account/forgot_password";
import Loading from "../../Loading.vue";

const isSending = ref(false);
const countdown = ref(60);
const globalAccountSelect = inject<Ref<string>>("globalAccountSelect");
const showLoading = ref(false);
const showErrorpanle = ref(false);
const errorPanleMsg = ref("");

const forgotFrom = ref({
    email: "",
    code: "",
    password: "",
    password_confirm: "",
});

const showLogin = () => {
    if (globalAccountSelect) {
        globalAccountSelect.value = "Login";
    }
};

const sendCode = () => {
    if (isSending.value) return;
    showLoading.value = true;
    isSending.value = true;
    countdown.value = 60;

    let timer: number;

    send_code(
        forgotFrom.value.email,
        // 成功回调
        () => {
            showLoading.value = false;
            // 启动倒计时
            timer = window.setInterval(() => {
                countdown.value -= 1;
                if (countdown.value <= 0) {
                    clearInterval(timer);
                    isSending.value = false;
                    countdown.value = 60; // 重置倒计时显示
                }
            }, 1000);
        },
        // 失败回调
        (message: string) => {
            showLoading.value = false;
            clearInterval(timer); // 清除可能存在的定时器
            isSending.value = false;
            countdown.value = 60;
            // 显示错误信息
            showErrorpanle.value = true;
            errorPanleMsg.value = message;
            setTimeout(() => {
                showErrorpanle.value = false;
                errorPanleMsg.value = "";
            }, 2500);
        },
    );
};

const forgotPassword = () => {
    forgot_password(
        forgotFrom.value,
        () => {
            showLogin();
        },
        (message: string) => {
            showErrorpanle.value = true;
            errorPanleMsg.value = message;
            setTimeout(() => {
                showErrorpanle.value = false;
                errorPanleMsg.value = "";
            }, 2500);
        },
    );
    console.log(forgotFrom.value);
};
</script>

<template>
    <div v-if="showLoading" class="loading-overlay">
        <Loading style="margin-left: 200px; margin-top: -120px" />
    </div>

    <p v-if="showErrorpanle" class="error-msg">{{ errorPanleMsg }}</p>

    <div class="form">
        <p class="title">忘记密码</p>
        <p class="message">输入您的电子邮件以重置密码</p>

        <label>
            <input
                v-model="forgotFrom.email"
                required
                placeholder=""
                type="email"
                class="input"
            />
            <span>电子邮箱</span>
        </label>

        <div class="code-container">
            <label style="flex: 1">
                <input
                    v-model="forgotFrom.code"
                    required
                    placeholder=""
                    type="text"
                    class="input"
                    maxlength="6"
                />
                <span>验证码</span>
            </label>
            <button
                class="code-btn"
                type="button"
                @click="sendCode"
                :disabled="isSending"
            >
                {{ isSending ? `${countdown}s` : "获取验证码" }}
            </button>
        </div>

        <label>
            <input
                v-model="forgotFrom.password"
                required
                placeholder=""
                type="password"
                class="input"
            />
            <span>新密码</span>
        </label>

        <label>
            <input
                v-model="forgotFrom.password_confirm"
                required
                placeholder=""
                type="password"
                class="input"
            />
            <span>重复新密码</span>
        </label>

        <button @click="forgotPassword()" class="submit">重置密码</button>
        <span @click="showLogin()">
            <p class="signin">
                回忆起密码? <a style="cursor: pointer">登录</a>
            </p>
        </span>
    </div>
</template>

<style scoped>
.form {
    display: flex;
    flex-direction: column;
    gap: 10px;
    width: 390px;
    background-color: #fff;
    padding: 20px;
    border-radius: 20px;
    position: relative;
    box-sizing: border-box;
}

.title {
    font-size: 28px;
    color: royalblue;
    font-weight: 600;
    letter-spacing: -1px;
    position: relative;
    display: flex;
    align-items: center;
    padding-left: 30px;
}

.title::before,
.title::after {
    position: absolute;
    content: "";
    height: 16px;
    width: 16px;
    border-radius: 50%;
    left: 0px;
    background-color: royalblue;
}

.title::before {
    width: 18px;
    height: 18px;
    background-color: royalblue;
}

.title::after {
    width: 18px;
    height: 18px;
    animation: pulse 1s linear infinite;
}

.message,
.signin {
    color: rgba(88, 87, 87, 0.822);
    font-size: 14px;
}

.signin {
    text-align: center;
}

.signin a {
    color: royalblue;
}

.signin a:hover {
    text-decoration: underline royalblue;
}

.flex {
    display: flex;
    width: 100%;
    gap: 6px;
}

.code-container {
    display: flex;
    align-items: center;
    gap: 10px;
    margin: 10px 0;
}

.code-btn {
    height: 44px;
    padding: 0 15px;
    background: royalblue;
    border: none;
    border-radius: 8px;
    color: white;
    cursor: pointer;
    transition: background 0.3s;
}

.code-btn:disabled {
    background: #cccccc;
    cursor: not-allowed;
}

.form label {
    position: relative;
}

.form label .input {
    width: 100%;
    padding: 10px 15px 20px 15px;
    outline: 0;
    border: 1px solid rgba(105, 105, 105, 0.397);
    border-radius: 10px;
    box-sizing: border-box;
}

.form label .input + span {
    position: absolute;
    left: 10px;
    top: 15px;
    color: grey;
    font-size: 0.9em;
    cursor: text;
    transition: 0.3s ease;
}

.form label .input:placeholder-shown + span {
    top: 15px;
    font-size: 0.9em;
}

.form label .input:focus + span,
.form label .input:valid + span {
    top: 30px;
    font-size: 0.7em;
    font-weight: 600;
}

.form label .input:valid + span {
    color: green;
}

.submit {
    border: none;
    outline: none;
    background-color: royalblue;
    padding: 10px;
    border-radius: 10px;
    color: #fff;
    font-size: 16px;
    transform: 0.3s ease;
    margin-top: 10px;
}

.submit:hover {
    background-color: rgb(56, 90, 194);
}

@keyframes pulse {
    from {
        transform: scale(0.9);
        opacity: 1;
    }

    to {
        transform: scale(1.8);
        opacity: 0;
    }
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
    color: #fff;
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
