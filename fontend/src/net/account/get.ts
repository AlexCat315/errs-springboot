import { defaultFailure, post } from "../post.js";

export async function get_account_info(
    success: Function,
    failure = defaultFailure,
) {
    post(`/api/admin/user/get/info`, {
    }, success, failure);
}
