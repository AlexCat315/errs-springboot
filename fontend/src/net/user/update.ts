import { defaultFailure, post } from "../post.js";

export async function update_user_role(
  user_id: number,
  role_id: string,
  success: Function,
  failure = defaultFailure,
) {
  post(`/api/admin/user/update/user/role`, {
    userId: user_id,
    role: role_id
  }, success, failure);
}
