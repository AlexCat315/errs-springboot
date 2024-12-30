import axios from 'axios'
import {ElMessage} from "element-plus";
import router from "../router/router.js";


const defaultError = (err) => {
    console.error(err)
    if (err.response) {
        switch (err.response.status) {
            case 401:
                ElMessage.warning('未授权，请重新登录');
                // 可以跳转到登录页面
                router.push('/login').then(r => {});
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

export const defaultFailure = (message, code, url) => {
    console.warn(`请求地址：${url},状态码：${code},错误信息：${message}`)
    ElMessage.warning(message)
}
function internalPost(url, data, header, success, failure, error = defaultError) {
    axios.post(url, data, {headers: header}).then(({data}) => {
        if (data.code === 200) {
            success(data.data)
        } else {
            failure(data.message)
        }
    }).catch(err => error(err))
}

function accessHeader() {
    if (localStorage.getItem('token') !== null) {
        return {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    }
    if (sessionStorage.getItem('token') !== null) {
        return {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${sessionStorage.getItem('token')}`
        }
    }
    return {
        'Content-Type': 'application/json'
    }
}

export function post(url, data, success, failure = defaultFailure) {
    internalPost(url, data, accessHeader(), success, failure)
}