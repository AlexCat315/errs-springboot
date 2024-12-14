import {defaultFailure, post} from "../Post.js";

export function logoutJS(success, failure = defaultFailure ){
    post("api/account/logout",{
    }, success, failure)
}