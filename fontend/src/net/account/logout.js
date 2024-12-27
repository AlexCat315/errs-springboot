import {defaultFailure, post} from "../post.js";

export function logoutJS(success, failure = defaultFailure ){
    post("api/account/logout",{
    }, success, failure)
}