import {defaultFailure, post} from "../post.js";

export function register(username, password, repeatPassword, email, code, success, failure = defaultFailure) {
    post("/api/account/register", {
        username: username,
        password: password,
        repeatPassword: repeatPassword,
        email: email,
        code: code
    }, success, failure)
}

export function getEmailCode(email, success, failure = defaultFailure ){
    post(`api/account/validate-email-register?email=${email}`,{
    }, success, failure)
}