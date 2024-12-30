import {post} from "./post.js";
import router from "../router/router.js";
import {ElMessage} from "element-plus";


/**
 * 验证token是否有效
 * */
export function verifyToken() {
    let token = localStorage.getItem('token');
    if (token === null || token === undefined || token === '') {
        token = sessionStorage.getItem('token');
    }
    post('/api/admin/account/validate-token', {}, () => {
        // token有效,不做任何操作
    }, () => {
        // token失效,删除token
        localStorage.removeItem('token');
        sessionStorage.removeItem('token');
        router.push('/login').then(r => {});
        ElMessage.warning('登录已失效,请重新登录');
    })
    return true;
}