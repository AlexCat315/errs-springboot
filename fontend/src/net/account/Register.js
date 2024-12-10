import {defaultFailure, post} from "../Post.js";

export function register(username, password, repeatPassword, success, failure = defaultFailure) {
    post("/api/account/register", {
        username: username,
        password: password,
        repeatPassword: repeatPassword
    }, success, failure)
}