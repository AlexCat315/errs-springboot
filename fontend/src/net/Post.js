import axios from 'axios'
import {ElMessage} from "element-plus";


export const defaultError = (err) => {
    console.warn(err)
    ElMessage.warning('发生了错误')
}
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
    return {
        'Content-Type': 'application/json',
    }
}

export function post(url, data, success, failure = defaultFailure) {
    internalPost(url, data, accessHeader(), success, failure)
}