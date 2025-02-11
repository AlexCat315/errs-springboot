import axios from 'axios'
import {ElMessage} from "element-plus";
import router from "../router/router.js";


/**
 * 默认错误处理函数
 * 用于处理各种API调用中遇到的错误响应
 * @param {Object} err - 错误对象，包含响应状态码等信息
 */
const defaultError = (err) => {
    // 判断是否有错误响应
    if (err.response) {
        // 根据不同的HTTP状态码进行相应的错误处理
        switch (err.response.status) {
            case 401:
                // 未授权错误，提示用户需要重新登录
                ElMessage.warning('未授权，请重新登录');
                // 可以跳转到登录页面
                router.push('/login').then(_r => {});
                break;
            case 403:
                // 权限不足错误，提示用户没有访问权限
                ElMessage.warning('权限不足，无法访问');
                break;
            case 500:
                // 服务器内部错误，提示具体错误信息
                ElMessage.error('服务器内部错误');
                break;
            default:
                // 其他错误，提供一般性错误提示
                ElMessage.warning('发生了错误');
        }
    } else {
        // 网络错误，提示用户网络连接问题
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
    const headers = {
        'Authorization': localStorage.getItem('token') 
            ? `Bearer ${localStorage.getItem('token')}` 
            : sessionStorage.getItem('token') 
                ? `Bearer ${sessionStorage.getItem('token')}` 
                : null,
    };

    // 如果已经设置了 Content-Type，则不覆盖
    if (!headers['Content-Type']) {
        headers['Content-Type'] = 'application/json';
    }

    return headers;
}

/**
 * 内部方法：发送 multipart/form-data 请求
 * @param {string} url - 请求地址
 * @param {FormData} data - FormData 对象
 * @param {Object} header - 请求头
 * @param {Function} success - 成功回调
 * @param {Function} failure - 失败回调
 * @param {Function} error - 错误处理函数
 */
function internalPostFormData(url, data, header, success, failure, error = defaultError) {
    axios.post(url, data, {
        headers: {
            ...header, // 合并自定义请求头
            'Content-Type': 'multipart/form-data', // 强制设置 Content-Type
        },
    }).then(({ data }) => {
        if (data.code === 200) {
            success(data.data);
        } else {
            failure(data.message, data.code, url);
        }
    }).catch(err => error(err));
}

/**
 * 发送 multipart/form-data 请求
 * @param {string} url - 请求地址
 * @param {FormData} data - FormData 对象
 * @param {Function} success - 成功回调
 * @param {Function} failure - 失败回调
 */
export function postFormData(url, data, success, failure = defaultFailure) {
    internalPostFormData(url, data, accessHeader(), success, failure);
}

export function post(url, data, success, failure = defaultFailure) {
    internalPost(url, data, accessHeader(), success, failure)
}