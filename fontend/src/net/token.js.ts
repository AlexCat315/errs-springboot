import {post} from "./post";
import router from "../router/router";


/**
 * 验证token是否有效
 * */
function verifyToken() {
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
        router.push('/login');
    })
    return true;
}