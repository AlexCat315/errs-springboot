import axios from 'axios'
import {ElMessage} from "element-plus";


const defaultError = (err) => {
    console.warn(err)
    ElMessage.warning('发生了错误')
}
const defaultFailure = (message, code, url) => {
    console.warn(`请求地址：${url},状态码：${code},错误信息：${message}`)
    ElMessage.warning(message)
}

function internalPost(url, data, header, success, failure, error = defaultError) {
    axios.post(url, data, {headers: header}).then(({data}) => {
        console.warn(data)
        if (data.code === 200) {
            success(data.data)
            // 向localStorage中存储token
            localStorage.setItem('token', data.data)
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

export function  login(username, password, remember, success, failure = defaultFailure) {
    internalPost('/api/account/login', {
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