<script setup>
import {reactive, ref} from "vue";
import {Unlock, User} from '@element-plus/icons-vue'
import router from "../../router/router.js";
import {login} from "../../net/account/login.js";


const form = reactive({
  username: '',
  password: '',
  remember: false
})

// 获取表单组件的引用
const formRef = ref();

const rule = {
  username: [
    {required: true, message: '请输入账号'}
  ],
  password: [
    {required: true, message: '请输入密码'}
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

  <div style="max-width: 100% ;max-height: 100vh;overflow: hidden;display: flex;font-family: system-ui;">
    <div style="flex:1;background-color: aquamarine;overflow-x: hidden;overflow-y: hidden;">
      <el-image style="width: 100%;height: 100vh;" fit="cover" src="../src/resources/login/login.webp"/>
      <div class="welcome-title">
        <div style="font-size: 30px;font-weight: bold">文娱评分与推荐系统</div>
        <div style="font-size: 10px">在这里你可以找到你喜欢的电影、电视剧、综艺节目、动漫、音乐等，并给出其评分和推荐。</div>
      </div>
    </div>
    <div class="right-card">
      <div style="text-align: center;margin: 0 20px ">
        <div style="margin-top: 150px;font-size: 25px;font-weight: bold">
          登录
        </div>
        <div style="font-size: 13px;color:#696A6E">请先输入用户名和密码</div>
        <div style="margin-top: 50px">
          <el-form ref="formRef" :model="form" :rules="rule">
            <el-form-item prop="username">
              <el-input v-model="form.username" maxlength="25" type="text" placeholder="用户/邮箱">
                <template #prefix>
                  <el-icon>
                    <User/>
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="form.password" maxlength="25" type="password" placeholder="密码">
                <template #prefix>
                  <el-icon>
                    <Unlock/>
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-row>
              <el-col :span="12" style="text-align: left">
                <el-form-item prop="remember">
                  <el-checkbox v-model="form. remember" label="记住我"/>
                </el-form-item>
              </el-col>
              <el-col :span="12" style="text-align: right">
                <el-link @click="routerPushForgotPassword">忘记密码</el-link>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div style="margin-top: 40px;">
          <el-button @click="userLogin" style="width: 200px;height: 40px" type="success" plain>登录</el-button>
        </div>
        <el-divider style="margin-top: 30px">
          <span style="font-size: 13px">没有账号？</span>
        </el-divider>
        <div style="margin-top: 40px">
          <el-button style="width: 200px" type="danger" plain @click="router.push('/register')">立即注册</el-button>
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped>
.right-card {
  width: 400px;
  z-index: auto;
  background-color: var(--el-bg-color);
}

.welcome-title {
  position: absolute;
  bottom: 40px;
  left: 30px;
  color: white;
  text-shadow: 0 0 10px black;
  text-align: center;
  font-size: 20px;
  font-weight: bold;

}
</style>