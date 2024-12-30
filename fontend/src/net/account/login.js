import axios from 'axios'
import {ElMessage} from "element-plus";


const defaultError = (err) => {
    console.error(err)
    if (err.response) {
        switch (err.response.status) {
            case 401:
                ElMessage.warning('未授权，请重新登录');
                // 可以跳转到登录页面
                window.location.href = '/login';
                break;
            case 403:
                ElMessage.warning('权限不足，无法访问');
                break;
            case 500:
                ElMessage.error('服务器内部错误');
                break;
            default:
                ElMessage.warning('发生了错误');
        }
    } else {
        ElMessage.warning('网络错误，请稍后重试');
    }
};

const defaultFailure = (message, code, url) => {
    console.warn(`请求地址：${url},状态码：${code},错误信息：${message}`)
    ElMessage.warning(message)
}

function internalPost(url, data, header, success, failure, error = defaultError) {
    axios.post(url, data, {headers: header, withCredentials: true}).then(({data}) => {
        if (data.code === 200) {
            success(data.data)
            // 如果开头为localStorage 则将token存储到localStorage中
            // 如果开头为sessionStorage 则将token存储到sessionStorage中
            if (data.data.startsWith('localStorage_')) {
                // 截取 token 部分，例如可能的格式是 localStorage_tokenValue
                const token = data.data.substring('localStorage_'.length);
                localStorage.setItem('token', token); // 存储 token 到 localStorage
            } else if (data.data.startsWith('sessionStorage_')) {
                // 截取 token 部分，例如可能的格式是 sessionStorage_tokenValue
                const token = data.data.substring('sessionStorage_'.length);
                sessionStorage.setItem('token', token); // 存储 token 到 sessionStorage
            }
        } else {
            failure(data.message)
        }
    }).catch(err => error(err))
}

function accessHeader() {
    return {
        'Content-Type': 'application/json',
    }
}

export function login(username, password, remember, success, failure = defaultFailure) {
    internalPost('/api/admin/account/login', {
            username: username,
            password: password,
            rememberMe: remember
        },
        accessHeader(),
        (data) => {
            ElMessage.success(`登录成功`)
            success(data)
        }, failure
    )
}