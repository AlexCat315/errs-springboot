import { defaultFailure, post } from "../post.js";

export async function get_user_info_all(
  page: number,
  size: number,
  success: Function,
  failure = defaultFailure,
) {
  post(`/api/admin/user/get/info/all`, {
    page: page,
    size: size
  }, success, failure);
}
