<script setup>
import { reactive, ref } from "vue";
import { Message, Unlock, User, ChatDotRound,Promotion } from '@element-plus/icons-vue'
import router from "../../router/router.js";
import { getEmailCode, register } from "../../net/account/register.js";
import { ElMessage } from "element-plus";


const form = reactive({
  username: '',
  password: '',
  repeatPassword: '',
  email: '',
  code: '',
  inviteCode: '',
})

// 获取表单组件的引用
const formRef = ref();

const rule = {
  username: [
    { required: true, message: '请输入账号' }
  ],
  password: [
    { required: true, message: '请输入密码' }
  ],
  repeatPassword: [
    { required: true, message: '请再次输入密码' }
  ],
  email: [
    { required: true, message: '请输入邮箱' }
  ],
  code: [
    { required: true, message: '请输入验证码' }
  ],
  inviteCode: [
    { required: true, message: '请输入邀请码' }
  ]
}

const validatePasswordKey = ref(-1)

function validatePassword() {
  if (form.password !== form.repeatPassword) {
    ElMessage.warning('两次输入的密码不一致')
  } else {
    validatePasswordKey.value = 1
  }
}

function userRegister() {
  validatePassword();
  if (validatePasswordKey.value === -1 ||
    form.password === '' ||
    form.repeatPassword === '' ||
    form.email === '' ||
    form.code === '') {
    ElMessage.warning('请填写完整信息')
    return
  } else {
    try {
      formRef.value.validate();
      // 验证通过，执行登录操作
      if (validatePasswordKey.value === 1) {
        register(form.username, form.password, form.repeatPassword, form.email, form.code, () => {
          // 处理登录成功后的逻辑
          router.push('/login')
          ElMessage.success('注册成功')
        });
      }
    } catch (error) {
      // 验证不通过，可以在这里处理错误
      console.log(error)
    }
  }
}

const isCode = ref(false)
const codeMessage = ref('获取验证码')
const validateCode = ref(0)
const loading = ref(false)

// 获取验证码
function getCode() {
  if (form.email === '') {
    return ElMessage.warning('请输入邮箱')
  }
  loading.value = true;
  // 发送验证码请求
  // 处理请求成功后的逻辑
  getEmailCode(form.email, () => {
    ElMessage.success('验证码发送成功');
    isCode.value = true;
    loading.value = false;
    // 验证码发送成功，倒计时60秒
    validateCode.value = 60;
    const timer = setInterval(() => {
      validateCode.value--;
      if (validateCode.value === 0) {
        clearInterval(timer);
        isCode.value = false;
      }
    }, 1000);
  }, (data) => {
    loading.value = false;
    ElMessage.error(data);
  })

}

</script>

<template>
  <div style="
      max-width: 100%;
      height: 100vh;
      overflow: hidden;
      display: flex;
      font-family: system-ui;
      position: relative;">
    <!-- 背景图片 -->
    <el-image style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: -1;" fit="cover"
      src="../src/resources/login/login.webp" />
    <!-- 登录卡片 -->
    <div>
      <div class="login_name">文娱推荐与评分系统 (管理端)</div>

      <el-card class="message-card">
        <template #header>
          <div class="card-header">
            <span style="font-family: 'yousu-title-black';font-size: 25px;color: #696A6E;">消息通知</span>
          </div>
        </template>
        <div class="block text-center">
          <el-carousel height="200px" motion-blur>
            <el-carousel-item>
              <p class="content-message">文娱推荐与评分系统管理端使用指南</p>
            </el-carousel-item>
            <el-carousel-item>
              <p class="content-message">关于系统安全，请阅读《文娱推荐与评分系统安全说明书》</p>
            </el-carousel-item>
            <el-carousel-item>
              <p class="content-message">进一步加强安全防范意识，落实安全准则</p>
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-card>
    </div>

    <div class="right-card">
      <div style="text-align: center;margin: 0 20px ">
        <div style="margin-top: 50px;font-size: 25px;font-weight: bold">
          加入我们
        </div>
        <div style="font-size: 13px;color:#696A6E">请完善注册信息</div>
        <div style="margin-top: 50px">
          <el-form ref="formRef" :model="form" :rules="rule">
            <el-form-item prop="username">
              <el-input v-model="form.username" maxlength="25" type="text" placeholder="用户/邮箱">
                <template #prefix>
                  <el-icon>
                    <User />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="form.password" maxlength="25" type="password" placeholder="密码">
                <template #prefix>
                  <el-icon>
                    <Unlock />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="form.repeatPassword" maxlength="25" type="password" placeholder="确认密码">
                <template #prefix>
                  <el-icon>
                    <Unlock />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="email">
              <el-input v-model="form.email" maxlength="100" type="text" placeholder="邮箱">
                <template #prefix>
                  <el-icon>
                    <Message />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>

            <div style="display: flex">
              <el-form-item prop="code">
                <el-input style="width: 200px" v-model="form.code" maxlength="6" type="text" placeholder="验证码">
                  <template #prefix>
                    <el-icon>
                      <ChatDotRound />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-button v-if="isCode === false && loading === false" @click="getCode"
                style="width: 100px;height: 33px;margin-left: 60px" color="#626aef" plain>
                {{ codeMessage }}
              </el-button>
              <el-button v-if="loading === true" style="width: 100px;height: 33px;margin-left: 60px" color="#626aef"
                loading>
                {{ codeMessage }}
              </el-button>
              <el-button v-if="isCode === true && loading === false" style="width: 100px;height: 33px;margin-left: 60px"
                color="#626aef" disabled>
                {{ validateCode }} 秒
              </el-button>
            </div>

            <el-form-item prop="inviteCode">
              <el-input v-model="form.inviteCode" maxlength="100" type="text" placeholder="邀请码（必填）">
                <template #prefix>
                  <el-icon>
                    <Promotion />
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>

          </el-form>
        </div>
        <div style="margin-top: 40px;">
          <el-button @click="userRegister" style="width: 200px;height: 40px" type="danger">立即注册</el-button>
        </div>

        <div style="margin-top: 20px">
          <el-button style="width: 200px;height: 40px" type="success" @click="router.push('/login')">返回登录
          </el-button>
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped>
.right-card {
  width: 400px;
  margin-left: 80px;
  margin-top: 60px;
  margin-bottom: 60px;
  z-index: 10;
  background-color: rgba(255, 255, 255, 0.2);
  /* 半透明背景 */
  backdrop-filter: blur(50px);
  /* 磨砂效果 */
  border-radius: 10px;
  /* 圆角边框 */
  padding: 20px;
  /* 内边距 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  /* 卡片阴影 */
  border: 1px solid rgba(255, 255, 255, 0.3);
  /* 淡白色边框 */
}

.message-card {
  width: 700px;
  height: 280px;
  z-index: auto;
  margin-left: 100px;
  margin-top: 50px;
  background-color: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(3px);
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.3);
}

.login_name {
  margin-top: 200px;
  margin-left: 100px;
  display: inline-block;
  font-family: 'MyFont1';
  font-size: 60px;
  line-height: 1em;
  color: #e07a5f;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  font-family: 'yousu-title-black';
}

.content-message {
  font-family: 'LXGWMarkerGothic-Regular';
}

.demonstration {
  color: var(--el-text-color-secondary);
}

.el-carousel__item h3 {
  color: transparent;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: transparent;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: transparent;
}
</style>
