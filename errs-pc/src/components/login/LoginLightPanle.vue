<script lang="ts" setup>
import { inject, ref, Ref, nextTick } from "vue";
import { login } from "../../net/account/login";
import Message from "../message/Message.vue";
import Loading from "../Loading.vue";
import QRCode from "qrcode";

const globalShowSetting = inject<Ref<boolean>>("globalShowSetting");
const globalSelect = inject<Ref<number>>("globalSelect");

const loginForm = ref({
  username: "",
  password: "",
});
const loginSubmit = async () => {
  showLoading.value = true;
  await login(
    loginForm.value,
    (data: any) => {
      console.log("login success", data);
      showLoading.value = false;
    },
    (message: string) => {
      showLoading.value = false;
      showMessage.value = true;
      messageInfo.value = message;
      messageType.value = "error";
      setTimeout(() => {
        showMessage.value = false;
      }, 2000);
      console.log("login fail" + message);
    }
  );
};

const submitGoBackSetting = () => {
  if (globalShowSetting && globalSelect) {
    globalShowSetting.value = true;
    globalSelect.value = 7;
  }
};

const showMessage = ref(false);
const messageInfo = ref("");
const messageType = ref("info");
const showLoading = ref(false);

const showAppVerify = ref(false);

const toAppVerify = async () => {
  showAppVerify.value = true;
  await nextTick();
  generateQRCode();
};

const qrcodeCanvas = ref<HTMLCanvasElement | null>(null);
const text = ref<string>("https://example.com");
const errorMessage = ref<string>("");
const qrOptions = ref({
  width: 200,
  color: {
    dark: "#000000",
    light: "#ffffff",
  },
});

const generateQRCode = () => {
  if (qrcodeCanvas.value) {
    text.value = "https://example.com"; // 使用固定的URL
    QRCode.toCanvas(
      qrcodeCanvas.value,
      text.value,
      qrOptions.value,
      (error) => {
        if (error) {
          errorMessage.value = "Failed to generate QR code: " + error.message;
        } else {
          errorMessage.value = "";
        }
      }
    );
  } else {
    errorMessage.value = "Canvas not found.";
  }
};
const toLogin = () => {
  showAppVerify.value = false;
};
</script>

<template>
  <div>
    <!-- Loading 遮罩层 -->
    <div v-if="showLoading" class="loading-overlay">
      <Loading style="margin-left: 200px; margin-top: -120px" />
    </div>

    <!-- 表单内容 -->
    <form class="form">
      <div v-if="!showAppVerify">
        <div class="flex-column" style="margin-top: 30px">
          <label style="margin-top: 30px">Email</label>
        </div>
        <div style="margin-top: 20px" class="inputForm">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            viewBox="0 0 32 32"
            height="20"
          >
            <g data-name="Layer 3" id="Layer_3">
              <path
                d="m30.853 13.87a15 15 0 0 0 -29.729 4.082 15.1 15.1 0 0 0 12.876 12.918 15.6 15.6 0 0 0 2.016.13 14.85 14.85 0 0 0 7.715-2.145 1 1 0 1 0 -1.031-1.711 13.007 13.007 0 1 1 5.458-6.529 2.149 2.149 0 0 1 -4.158-.759v-10.856a1 1 0 0 0 -2 0v1.726a8 8 0 1 0 .2 10.325 4.135 4.135 0 0 0 7.83.274 15.2 15.2 0 0 0 .823-7.455zm-14.853 8.13a6 6 0 1 1 6-6 6.006 6.006 0 0 1 -6 6z"
              ></path>
            </g>
          </svg>
          <input
            v-model="loginForm.username"
            placeholder="邮箱账号"
            class="input"
            type="text"
          />
        </div>

        <div style="margin-top: 30px" class="flex-column">
          <label>Password</label>
        </div>
        <div style="margin-top: 20px" class="inputForm">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            viewBox="-64 0 512 512"
            height="20"
          >
            <path
              d="m336 512h-288c-26.453125 0-48-21.523438-48-48v-224c0-26.476562 21.546875-48 48-48h288c26.453125 0 48 21.523438 48 48v224c0 26.476562-21.546875 48-48 48zm-288-288c-8.8125 0-16 7.167969-16 16v224c0 8.832031 7.1875 16 16 16h288c8.8125 0 16-7.167969 16-16v-224c0-8.832031-7.1875-16-16-16zm0 0"
            ></path>
            <path
              d="m304 224c-8.832031 0-16-7.167969-16-16v-80c0-52.929688-43.070312-96-96-96s-96 43.070312-96 96v80c0 8.832031-7.167969 16-16 16s-16-7.167969-16-16v-80c0-70.59375 57.40625-128 128-128s128 57.40625 128 128v80c0 8.832031-7.167969 16-16 16zm0 0"
            ></path>
          </svg>
          <input
            v-model="loginForm.password"
            placeholder="密码"
            class="input"
            type="password"
          />
        </div>

        <div class="flex-row">
          <div></div>
          <span style="margin-top: 10px" class="span">忘记密码?</span>
        </div>
        <Message
          v-if="showMessage"
          :type="messageType"
          :message="messageInfo"
          width="180px"
          className="error-msg"
        />
        <button @click="loginSubmit()" type="button" class="button-submit">
          登 录
        </button>
        <p class="p">没有账号? <span class="span">立即注册</span></p>
        <p class="p line">或</p>

        <div class="flex-row">
          <button class="btn google">
            <svg
              xml:space="preserve"
              style="enable-background: new 0 0 512 512"
              viewBox="0 0 512 512"
              y="0px"
              x="0px"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              xmlns="http://www.w3.org/2000/svg"
              id="Layer_1"
              width="20"
              version="1.1"
            >
              <path
                d="M113.47,309.408L95.648,375.94l-65.139,1.378C11.042,341.211,0,299.9,0,256
	c0-42.451,10.324-82.483,28.624-117.732h0.014l57.992,10.632l25.404,57.644c-5.317,15.501-8.215,32.141-8.215,49.456
	C103.821,274.792,107.225,292.797,113.47,309.408z"
                style="fill: #fbbb00"
              ></path>
              <path
                d="M507.527,208.176C510.467,223.662,512,239.655,512,256c0,18.328-1.927,36.206-5.598,53.451
	c-12.462,58.683-45.025,109.925-90.134,146.187l-0.014-0.014l-73.044-3.727l-10.338-64.535
	c29.932-17.554,53.324-45.025,65.646-77.911h-136.89V208.176h138.887L507.527,208.176L507.527,208.176z"
                style="fill: #518ef8"
              ></path>
              <path
                d="M416.253,455.624l0.014,0.014C372.396,490.901,316.666,512,256,512
	c-97.491,0-182.252-54.491-225.491-134.681l82.961-67.91c21.619,57.698,77.278,98.771,142.53,98.771
	c28.047,0,54.323-7.582,76.87-20.818L416.253,455.624z"
                style="fill: #28b446"
              ></path>
              <path
                d="M419.404,58.936l-82.933,67.896c-23.335-14.586-50.919-23.012-80.471-23.012
	c-66.729,0-123.429,42.957-143.965,102.724l-83.397-68.276h-0.014C71.23,56.123,157.06,0,256,0
	C318.115,0,375.068,22.126,419.404,58.936z"
                style="fill: #f14336"
              ></path>
            </svg>
            Google
          </button>
          <button @click="toAppVerify()" class="btn apple">
            <svg
              t="1737180514049"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="3186"
              width="25"
              height="25"
            >
              <path
                d="M512 1024C229.227 1024 0 794.773 0 512S229.227 0 512 0s512 229.227 512 512-229.227 512-512 512zM199.424 489.75a21.333 21.333 0 0 0-21.333 21.333v1.834a21.333 21.333 0 0 0 21.333 21.334h625.152a21.333 21.333 0 0 0 21.333-21.334v-1.834a21.333 21.333 0 0 0-21.333-21.334H199.424zM623.36 267.135h66.73a66.773 66.773 0 0 1 66.774 66.773v89.046a22.25 22.25 0 1 0 44.523 0v-89.046A111.296 111.296 0 0 0 690.09 222.613H623.36a22.25 22.25 0 1 0 0 44.523z m133.504 356.16v66.773a66.773 66.773 0 0 1-66.773 66.795h-66.774a22.25 22.25 0 1 0 0 44.523h66.774A111.296 111.296 0 0 0 801.387 690.09v-66.774a22.25 22.25 0 0 0-44.523 0z m-356.16 133.568h-66.773a66.773 66.773 0 0 1-66.795-66.773v-66.774a22.25 22.25 0 1 0-44.523 0v66.774A111.296 111.296 0 0 0 333.91 801.387h66.774a22.25 22.25 0 0 0 0-44.523z m-133.568-356.16v-66.773a66.773 66.773 0 0 1 66.773-66.795h66.774a22.25 22.25 0 1 0 0-44.523h-66.774A111.296 111.296 0 0 0 222.613 333.91v66.774a22.25 22.25 0 0 0 44.523 0z"
                fill="#333333"
                p-id="3187"
              ></path>
            </svg>
            APP扫码
          </button>
        </div>
        <button
          @click="submitGoBackSetting()"
          type="button"
          class="button-submit-back"
        >
          返回设置
        </button>
      </div>
      <!-- app 扫码 -->
      <div v-if="showAppVerify" class="app-verify-container">
        <h4>请使用APP扫码登录</h4>
        <div>
          <canvas ref="qrcodeCanvas"></canvas>
        </div>
        <p @click="toLogin()" class="clickable-text">使用账号密码登录</p>
      </div>
    </form>
  </div>
</template>

<style lang="css" scoped>
canvas {
  border: 1px solid #ccc;
  margin-top: 20px;
}
/* 表单样式 */
.form {
  position: relative;
  /* 确保表单内容在遮罩层下方 */
  display: flex;
  flex-direction: column;
  gap: 10px;
  background-color: #ffffff;
  padding: 30px;
  width: 450px;
  border-radius: 20px;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
}

/* Loading 遮罩层 */
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

::placeholder {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
}

.flex-column > label {
  color: #151717;
  font-weight: 600;
}

.inputForm {
  border: 1.5px solid #ddd;
  border-radius: 10px;
  height: 50px;
  display: flex;
  align-items: center;
  padding-left: 10px;
  transition: 0.2s ease-in-out;
}

.input {
  margin-left: 10px;
  border-radius: 10px;
  border: none;
  width: 100%;
  height: 100%;
}

.input:focus {
  outline: none;
}

.inputForm:focus-within {
  border: 1.5px solid #2d79f3;
}

.flex-row {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 10px;
  justify-content: space-between;
}

.span {
  font-size: 14px;
  margin-left: 5px;
  color: #2d79f3;
  font-weight: 500;
  cursor: pointer;
}

.button-submit {
  margin: 20px 0 10px 0;
  background-color: #151717;
  border: none;
  color: white;
  font-size: 15px;
  font-weight: 500;
  border-radius: 10px;
  height: 50px;
  width: 100%;
  cursor: pointer;
}

.button-submit-back {
  margin: 20px 0 10px 0;
  background-color: #096565;
  border: none;
  color: white;
  font-size: 15px;
  font-weight: 500;
  border-radius: 10px;
  height: 50px;
  width: 100%;
  cursor: pointer;
}

.p {
  text-align: center;
  color: black;
  font-size: 14px;
  margin: 5px 0;
}

.btn {
  margin-top: 10px;
  width: 100%;
  height: 50px;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: 500;
  gap: 10px;
  border: 1px solid #ededef;
  background-color: white;
  cursor: pointer;
  transition: 0.2s ease-in-out;
}

.btn:hover {
  border: 1px solid #2d79f3;
}

@keyframes moveUp {
  0% {
    transform: translateY(0);
  }

  100% {
    transform: translateY(-50px);
  }
}

.error-msg {
  margin-left: 120px;
  margin-bottom: -70px;
  margin-top: 30px;
  animation: moveUp 0.4s ease-in-out forwards;
}

.app-verify-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.clickable-text {
  color: #518ef8;
  cursor: pointer;
}

.clickable-text:hover {
  text-decoration: underline;
}

.clickable-text.active {
  text-decoration: underline;
}
</style>
