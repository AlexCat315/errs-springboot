import {defaultFailure, post} from "../Post.js";

export function forgotPassword( password, repeatPassword, email, code, success, failure = defaultFailure) {
    post("/api/account/forgot-password", {
        password: password,
        confirmPassword: repeatPassword,
        email: email,
        code: code
    }, success, failure)
}


export function getEmailCode(email, success, failure = defaultFailure ){
    post(`api/account/validate-email-forgot-password?email=${email}`,{
    }, success, failure)
}