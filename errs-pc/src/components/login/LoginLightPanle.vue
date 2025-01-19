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
      localStorage.setItem("token", data.token);
      if (globalShowSetting) {
        globalShowSetting.value = true;
      }
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
const showQRCode = ref(false);
const showWeChatVerify = ref(false);

const toAppVerify = async () => {
  showQRCode.value = true;
  showAppVerify.value = true;
  await nextTick();
  generateQRCode();
};

const toWeChatVerify = async () => {
  showQRCode.value = true;
  showWeChatVerify.value = true;
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
  showQRCode.value = false;
  showWeChatVerify.value = false;
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
      <div v-if="!showQRCode">
        <div class="flex-column" style="margin-top: 30px">
          <label style="margin-top: 30px">Email</label>
        </div>
        <div style="margin-top: 20px" class="inputForm">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" viewBox="0 0 32 32" height="20">
            <g data-name="Layer 3" id="Layer_3">
              <path
                d="m30.853 13.87a15 15 0 0 0 -29.729 4.082 15.1 15.1 0 0 0 12.876 12.918 15.6 15.6 0 0 0 2.016.13 14.85 14.85 0 0 0 7.715-2.145 1 1 0 1 0 -1.031-1.711 13.007 13.007 0 1 1 5.458-6.529 2.149 2.149 0 0 1 -4.158-.759v-10.856a1 1 0 0 0 -2 0v1.726a8 8 0 1 0 .2 10.325 4.135 4.135 0 0 0 7.83.274 15.2 15.2 0 0 0 .823-7.455zm-14.853 8.13a6 6 0 1 1 6-6 6.006 6.006 0 0 1 -6 6z">
              </path>
            </g>
          </svg>
          <input v-model="loginForm.username" placeholder="邮箱账号" class="input" type="text" />
        </div>

        <div style="margin-top: 30px" class="flex-column">
          <label>Password</label>
        </div>
        <div style="margin-top: 20px" class="inputForm">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" viewBox="-64 0 512 512" height="20">
            <path
              d="m336 512h-288c-26.453125 0-48-21.523438-48-48v-224c0-26.476562 21.546875-48 48-48h288c26.453125 0 48 21.523438 48 48v224c0 26.476562-21.546875 48-48 48zm-288-288c-8.8125 0-16 7.167969-16 16v224c0 8.832031 7.1875 16 16 16h288c8.8125 0 16-7.167969 16-16v-224c0-8.832031-7.1875-16-16-16zm0 0">
            </path>
            <path
              d="m304 224c-8.832031 0-16-7.167969-16-16v-80c0-52.929688-43.070312-96-96-96s-96 43.070312-96 96v80c0 8.832031-7.167969 16-16 16s-16-7.167969-16-16v-80c0-70.59375 57.40625-128 128-128s128 57.40625 128 128v80c0 8.832031-7.167969 16-16 16zm0 0">
            </path>
          </svg>
          <input v-model="loginForm.password" placeholder="密码" class="input" type="password" />
        </div>

        <div class="flex-row">
          <div></div>
          <span style="margin-top: 10px" class="span">忘记密码?</span>
        </div>
        <Message v-if="showMessage" :type="messageType" :message="messageInfo" width="180px" className="error-msg" />
        <button @click="loginSubmit()" type="button" class="button-submit">
          登 录
        </button>
        <p class="p">没有账号? <span class="span">立即注册</span></p>
        <p class="p line">或</p>

        <div class="flex-row">
          <button @click="toWeChatVerify" class="btn google">
            <svg t="1737203952284" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
              p-id="2028" width="30" height="30">
              <path
                d="M512 2C230.6 2 2 230.6 2 512s228.6 510 510 510 510-228.6 510-510S793.3 2 512 2z m-93.5 644.3c-24.6 0-49.3-4.3-71.4-10.2-1.7 0-4.3-1.7-4.3-1.7l-55.3 32.3c-14.4 8.5-24.7 1.7-20.4-14.4 0 0 14.4-59.5 11.9-59.5-52.7-34.9-77.4-79.9-77.4-141.1 4.3-102.8 94.4-184.5 216.8-184.5 112.2 0 204 69.7 220.2 161.5h-10.2c-112.2 0-204 77.4-204 173.4 0 16.1 1.7 30.6 6 45h-11.9v-0.8z m328.1 79.9l16.1 32.3c8.5 14.5 1.7 20.4-11.9 11.9 0 0-40.8-24.7-42.5-22.1-18.7 6-59.5 11.9-79.9 11.9-103.7 0-189.6-71.4-189.6-159s85.9-159 189.6-159S818 513.7 818 601.3c0 51.8-26.3 94.3-71.4 124.9zM353 363.3c-8.5-5.1-19.5-5.1-28.9 0-8.5 5.1-14.4 14.4-14.4 24.6s5.1 19.6 14.4 24.7c8.5 5.1 19.6 5.1 28.9 0 8.5-5.1 14.5-14.5 14.5-24.7s-6-19.6-14.5-24.6zM510.3 416c16.2 0 28.9-12.8 28.9-28.9 0-10.2-5.1-19.5-14.4-24.6-8.5-5.1-19.6-5.1-28.9 0-9.3 5.1-14.4 14.4-14.4 24.6-0.1 16.1 12.6 28.9 28.8 28.9z m187 100.3c-13.6 0-24.6 11-24.6 24.6s11 24.6 24.6 24.6 24.7-11 24.7-24.6-11.1-24.6-24.7-24.6z m-134.3 0c-13.6 0-24.6 11-24.6 24.6s11 24.6 24.6 24.6 24.6-11 24.6-24.6-11-24.6-24.6-24.6z"
                fill="#08BA06" p-id="2029"></path>
            </svg>
            微信扫码
          </button>
          <button @click="toAppVerify()" class="btn apple">
            <svg t="1737203464746" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
              p-id="4296" data-spm-anchor-id="a313x.search_index.0.i1.d39c3a81HGptt2" width="30" height="30">
              <path
                d="M903.825478 533.599947l-783.637654 0c-16.641005 0-30.147624-13.494339-30.147624-30.133298 0-16.625656 13.507642-30.148647 30.147624-30.148647l783.637654 0c16.670681 0 30.133298 13.522991 30.133298 30.148647C933.958776 520.105608 920.49616 533.599947 903.825478 533.599947M813.426609 895.284453 647.261125 895.284453c-16.670681 0-30.163997-13.462616-30.163997-30.132274 0-16.670681 13.493316-30.163997 30.163997-30.163997l166.166507 0c16.641005 0 30.102598-13.492292 30.102598-30.132274L843.530231 654.16179c0-16.671705 13.492292-30.133298 30.162974-30.133298 16.672728 0 30.133298 13.461593 30.133298 30.133298l0 150.694117C903.825478 854.717526 863.258551 895.284453 813.426609 895.284453M382.300544 895.284453 210.602044 895.284453c-49.861618 0-90.413196-40.566928-90.413196-90.428545L120.188848 654.16179c0-16.671705 13.507642-30.133298 30.147624-30.133298 16.641005 0 30.117948 13.461593 30.117948 30.133298l0 150.694117c0 16.641005 13.537318 30.132274 30.147624 30.132274L382.300544 834.988182c16.670681 0 30.163997 13.493316 30.163997 30.163997C412.463518 881.821837 398.970202 895.284453 382.300544 895.284453M150.336472 382.903783c-16.639982 0-30.147624-13.507642-30.147624-30.148647L120.188848 202.061019c0-49.861618 40.552601-90.413196 90.413196-90.413196L382.300544 111.647823c16.670681 0 30.163997 13.506619 30.163997 30.147624 0 16.610306-13.493316 30.117948-30.163997 30.117948L210.602044 171.913395c-16.610306 0-30.147624 13.537318-30.147624 30.147624l0 150.694117C180.45442 369.396141 166.976454 382.903783 150.336472 382.903783M873.692181 382.903783c-16.670681 0-30.162974-13.507642-30.162974-30.148647L843.529207 202.061019c0-16.610306-13.461593-30.147624-30.102598-30.147624L647.261125 171.913395c-16.670681 0-30.163997-13.507642-30.163997-30.117948 0-16.641005 13.493316-30.147624 30.163997-30.147624l166.166507 0c49.830919 0 90.39887 40.552601 90.39887 90.413196l0 150.694117C903.825478 369.396141 890.364909 382.903783 873.692181 382.903783"
                fill="#333333" p-id="4297" data-spm-anchor-id="a313x.search_index.0.i2.d39c3a81HGptt2" class="selected">
              </path>
            </svg>
            APP扫码
          </button>
        </div>
        <button @click="submitGoBackSetting()" type="button" class="button-submit-back">
          返回设置
        </button>
      </div v-if="showQRCode">
      <!-- app 扫码 -->
      <div>
        <div v-if="showAppVerify" class="app-verify-container">
          <h4>请使用APP扫码登录</h4>
          <div>
            <canvas ref="qrcodeCanvas"></canvas>
          </div>
          <p @click="toLogin()" class="clickable-text">使用账号密码登录</p>
        </div>
        <div v-if="showWeChatVerify" class="app-verify-container">
          <div style="display: flex; justify-content: space-between;">
            <h4>请打开手机</h4>
            <h4 style="color:#2d79f3;">微信</h4>
            <h4>扫码登录</h4>
          </div>
          <div>
            <canvas ref="qrcodeCanvas"></canvas>
          </div>
          <p @click="toLogin()" class="clickable-text">使用账号密码登录</p>
        </div>
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

.flex-column>label {
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
