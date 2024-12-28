import {defaultFailure, post} from "../post.js";

export function forgotPassword( password, repeatPassword, email, code, success, failure = defaultFailure) {
    post("/api/admin/account/forgot-password", {
        password: password,
        confirmPassword: repeatPassword,
        email: email,
        code: code
    }, success, failure)
}


export function getEmailCode(email, success, failure = defaultFailure ){
    post(`api/admin/account/validate-email-forgot-password?email=${email}`,{
    }, success, failure)
}