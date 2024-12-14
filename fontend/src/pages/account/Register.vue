<script setup>
import {reactive, ref} from "vue";
import {Message, Unlock, User} from '@element-plus/icons-vue'
import router from "../../router/router.js";
import {getEmailCode, register} from "../../net/account/Register.js";
import {ElMessage} from "element-plus";


const form = reactive({
  username: '',
  password: '',
  repeatPassword: '',
  email: '',
  code: ''
})

// 获取表单组件的引用
const formRef = ref();

const rule = {
  username: [
    {required: true, message: '请输入账号'}
  ],
  password: [
    {required: true, message: '请输入密码'}
  ],
  repeatPassword: [
    {required: true, message: '请再次输入密码'}
  ],
  email: [
    {required: true, message: '请输入邮箱'}
  ],
  code: [
    {required: true, message: '请输入验证码'}
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
          加入我们
        </div>
        <div style="font-size: 13px;color:#696A6E">请完善注册信息</div>
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
            <el-form-item prop="password">
              <el-input v-model="form.repeatPassword" maxlength="25" type="password" placeholder="确认密码">
                <template #prefix>
                  <el-icon>
                    <Unlock/>
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="email">
              <el-input v-model="form.email" maxlength="100" type="text" placeholder="邮箱">
                <template #prefix>
                  <el-icon>
                    <Message/>
                  </el-icon>
                </template>
              </el-input>
            </el-form-item>

            <div style="display: flex">
              <el-form-item prop="code">
                <el-input style="width: 200px" v-model="form.code" maxlength="6" type="text" placeholder="验证码">
                  <template #prefix>
                    <el-icon>
                      <Message/>
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

          </el-form>
        </div>
        <div style="margin-top: 40px;">
          <el-button @click="userRegister" style="width: 200px;height: 40px" type="success" plain>立即注册</el-button>
        </div>

        <div style="margin-top: 20px">
          <el-button style="width: 200px;height: 40px" type="danger" plain @click="router.push('/login')">返回登录
          </el-button>
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