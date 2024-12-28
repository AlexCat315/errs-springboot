<script setup>
import { reactive, ref } from "vue";
import { Unlock, User } from '@element-plus/icons-vue'
import router from "../../router/router.js";
import { login } from "../../net/account/login.js";


const form = reactive({
  username: '',
  password: '',
  remember: false
})

// 获取表单组件的引用
const formRef = ref();

const rule = {
  username: [
    { required: true, message: '请输入账号' }
  ],
  password: [
    { required: true, message: '请输入密码' }
  ]
}

function userLogin() {
  try {
    formRef.value.validate();
    // 验证通过，执行登录操作
    login(form.username, form.password, form.remember, (data) => {
      // 处理登录成功后的逻辑
      router.push('/index')


    });
  } catch (error) {
    // 验证不通过，可以在这里处理错误
    console.log(error)
  }
}

function routerPushForgotPassword() {
  router.push('/forgot-password')
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
            <el-carousel-item >
              <p class="content-message">文娱推荐与评分系统管理端使用指南</p>
            </el-carousel-item>
            <el-carousel-item >
              <p class="content-message">关于系统安全，请阅读《文娱推荐与评分系统安全说明书》</p>
            </el-carousel-item>
            <el-carousel-item >
              <p class="content-message">进一步加强安全防范意识，落实安全准则</p>
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-card>

    </div>

    <div class="right-card">
      <div style="text-align: center; margin: 0 20px;">
        <div style="margin-top: 130px; font-size: 25px; font-weight: bold;">登录</div>
        <div style="font-size: 13px; color:#696A6E">请输入用户名和密码</div>
        <div style="margin-top: 50px;">
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
            <el-row>
              <el-col :span="12" style="text-align: left">
                <el-form-item prop="remember">
                  <el-checkbox v-model="form.remember" label="记住我" />
                </el-form-item>
              </el-col>
              <el-col :span="12" style="text-align: right">
                <el-link @click="routerPushForgotPassword">忘记密码</el-link>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div style="margin-top: 40px;">
          <el-button @click="userLogin" style="width: 200px; height: 40px;" type="success">登录</el-button>
        </div>
        <el-link href="/register" style="font-size: 13px;margin-top: 20px;">没有账号？</el-link>
        <div style="margin-top: 20px;margin-bottom: 40px;">
          <el-button style="width: 200px;height: 40px;" type="danger" @click="router.push('/register')">立即注册</el-button>
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
