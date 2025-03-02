<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue'
import { get_account_info } from '../../../net/account/get';
import { postFormData } from '../../../net/post';

interface User {
    id: number;
    username: string;
    avatar: string;
    email: string;
    createdAt: string;
    role: string;
}

// 模拟用户数据
const user = reactive<User>({
    id: 1,
    username: 'VueMaster',
    avatar: 'https://example.com/avatar.jpg',  // 替换为你的默认头像或占位图 URL
    email: 'vue@example.com',
    createdAt: '2023-01-01',
    role: 'admin'
})

// 修改密码相关
const showPasswordForm = ref(false)
const passwordForm = reactive({
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
})

// 头像上传相关
const avatarFile = ref<File | null>(null)
const avatarPreview = ref<string>('') // 用于预览的 DataURL
const uploadStatus = ref('') // 可以用来显示 "上传中..." 等状态

// 错误提示
const errorMessage = ref('')
const successMessage = ref('')

// 处理头像选择
const handleAvatarSelect = (event: Event) => {
    const input = event.target as HTMLInputElement
    if (input.files && input.files[0]) {
        const file = input.files[0]
        if (!file.type.startsWith('image/')) {
            errorMessage.value = '请选择图片文件'
            return
        }
        avatarFile.value = file
        const reader = new FileReader()
        reader.onload = (e) => {
            avatarPreview.value = e.target?.result as string
            // 选择了新头像，立即显示预览，不再立即更新 user.avatar.  在上传成功后更新
        }
        reader.readAsDataURL(file)
        errorMessage.value = '' // 清除之前的错误
        successMessage.value = ''; // 清除成功的消息
    }
}

// 上传头像
// 修改后的上传方法
const uploadAvatar = async () => {
    if (!avatarFile.value) {
        errorMessage.value = '请先选择头像文件'
        return
    }

    uploadStatus.value = '上传中...'
    errorMessage.value = ''
    successMessage.value = ''

    try {
        const formData = new FormData()
        formData.append('img', avatarFile.value)

        // 调用封装的 postFormData 方法
        postFormData(
            '/api/admin/user/update/user/img', // Spring Boot 接口地址
            formData,
            (data: any) => {
                user.avatar = data
                successMessage.value = '头像上传成功'
                avatarFile.value = null
                // 清空文件选择
                const avatarInput = document.querySelector('#avatarInput') as HTMLInputElement
                if (avatarInput) avatarInput.value = ''
                // 显示上传成功的预览图
                avatarPreview.value = data
                setTimeout(() => {
                    successMessage.value = "";
                }, 2000);

            },
            (error: string) => {
                errorMessage.value = error || '头像上传失败'
                setTimeout(() => {
                    errorMessage.value = "";
                }, 2000);
            }
        )
    } catch (err) {
        errorMessage.value = '上传请求创建失败'
        console.error(err)
        setTimeout(() => {
            errorMessage.value = "";
        }, 2000);
    } finally {
        uploadStatus.value = '';

    }
}

// 提交修改密码
const submitPasswordChange = () => {
    if (passwordForm.newPassword !== passwordForm.confirmPassword) {
        errorMessage.value = '两次输入的新密码不一致'
        return
    }
    if (passwordForm.newPassword.length < 6) {
        errorMessage.value = "新密码长度不能小于6位";
        return;
    }

    successMessage.value = '';
    errorMessage.value = '';

    // 这里应该调用修改密码API
    try {
        // 模拟API调用
        setTimeout(() => {
            // 假设这里是 API 返回的成功结果
            successMessage.value = '密码修改成功'
            showPasswordForm.value = false
            passwordForm.oldPassword = ''
            passwordForm.newPassword = ''
            passwordForm.confirmPassword = ''
        }, 1000)
    } catch (err) {
        errorMessage.value = '密码修改失败，请检查旧密码'  // 更友好的错误提示
    }
}

// 重置表单 (可选)
const resetPasswordForm = () => {
    passwordForm.oldPassword = '';
    passwordForm.newPassword = '';
    passwordForm.confirmPassword = '';
    errorMessage.value = '';
    successMessage.value = '';
}

onMounted(() => {
    get_account_info((data: any) => {
        console.log(data);
        user.email = data.email;
        user.role = data.role;
        user.createdAt = data.createdAt;
        user.avatar = data.userUrl;
        user.username = data.username;
    })
})
</script>

<template>
    <div class="user-center">
        <!-- 错误/成功 提示 -->
        <div v-if="errorMessage" class="alert alert-danger">{{ errorMessage }}</div>
        <div v-if="successMessage" class="alert alert-success">{{ successMessage }}</div>
        <div v-if="uploadStatus" class="alert alert-info">{{ uploadStatus }}</div>

        <!-- 用户信息展示 -->
        <div class="user-info">
            <h1 class="text-center mb-4">用户信息</h1> <!-- 居中标题 -->
            <div class="avatar-section">
                <img :src="avatarPreview || user.avatar" alt="" class="avatar" />
                <div class="avatar-actions">
                    <input type="file" accept="image/*" @change="handleAvatarSelect" ref="avatarInput" id="avatarInput"
                        hidden />
                    <button class="btn btn-primary" @click="() => ($refs.avatarInput as HTMLInputElement).click()">
                        选择头像
                    </button>
                    <button class="btn btn-success" @click="uploadAvatar" :disabled="!avatarFile">
                        上传头像
                    </button>
                </div>
            </div>

            <div class="info-item">
                <label class="info-label">用户名：</label>
                <span class="info-value">{{ user.username }}</span>
            </div>
            <div class="info-item">
                <label class="info-label">邮箱：</label>
                <span class="info-value">{{ user.email }}</span>
            </div>
            <div class="info-item">
                <label class="info-label">用户权限：</label>
                <span class="info-value">{{ user.role }}</span>
            </div>
            <div class="info-item">
                <label class="info-label">注册时间：</label>
                <span class="info-value">{{ user.createdAt }}</span>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* 整体布局 */
.user-center {
    max-width: 600px;
    margin: 2rem auto;
    padding: 2rem;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    background-color: #fff;
}

/* 头像部分 */
.avatar-section {
    display: flex;
    align-items: center;
    margin-bottom: 1.5rem;
}

.avatar {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    object-fit: cover;
    border: 3px solid #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-right: 1rem;
}

.avatar-actions {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
}


/* 信息条目 */
.info-item {
    display: flex;
    align-items: center;
    padding: 0.75rem 0;
    border-bottom: 1px solid #eee;
}

.info-item:last-child {
    border-bottom: none;
}

.info-label {
    font-weight: bold;
    width: 120px;
    /* 固定标签宽度 */
    color: #555;
    font-size: 16px;
}

.info-value {
    color: #333;
    font-size: 14px;
}

/* 表单容器 */
.password-form-container {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 1.5rem;
    margin-top: 20px;
    background-color: #f8f9fa;
}

/* 表单样式 */
.form-group {
    margin-bottom: 1rem;
}

.form-label {
    display: block;
    margin-bottom: 0.5rem;
    font-weight: bold;
}

.form-control {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 1rem;
}

.form-actions {
    display: flex;
    justify-content: flex-end;
    /* 按钮靠右 */
    gap: 0.5rem;
    margin-top: 1rem;
}

/* 按钮 */
.btn {
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1rem;
    transition: background-color 0.2s;
}

.btn-primary {
    background-color: #007bff;
    color: white;
}

.btn-primary:hover {
    background-color: #0056b3;
}

.btn-secondary {
    background-color: #6c757d;
    color: white;
}

.btn-secondary:hover {
    background-color: #5a6268;
}

.btn-success {
    background-color: #28a745;
    color: white;
}

.btn-success:hover {
    background-color: #218838;
}

.btn-info {
    background-color: #17a2b8;
    color: white;
}

.btn-info:hover {
    background-color: #138496;
}


.btn:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}

/* 提示信息 */
.alert {
    padding: 0.75rem 1.25rem;
    margin-bottom: 1rem;
    border: 1px solid transparent;
    border-radius: 0.25rem;
}

.alert-danger {
    color: #721c24;
    background-color: #f8d7da;
    border-color: #f5c6cb;
}

.alert-success {
    color: #155724;
    background-color: #d4edda;
    border-color: #c3e6cb;
}

.alert-info {
    color: #0c5460;
    background-color: #d1ecf1;
    border-color: #bee5eb;
}

/* 其他 */
.mb-4 {
    margin-bottom: 1.5rem;
}

.mt-3 {
    margin-top: 1rem;
}

.text-center {
    text-align: center;
}
</style>