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

export async function update_user_password(
  user_id: number,
  success: Function,
  failure = defaultFailure,
) {
  post(`/api/admin/user/update/reset-password?userId=${user_id}`, {
  }, success, failure);
}

// 封禁
export async function update_user_banned(
  user_id: number,
  success: Function,
  failure = defaultFailure,
) {
  post(`/api/admin/user/update/ban-account?userId=${user_id}`, {
  }, success, failure);
}

//解封
export async function update_user_unbanned(
  user_id: number,
  success: Function,
  failure = defaultFailure,
) {
  post(`/api/admin/user/update/unban-account?userId=${user_id}`, {
  }, success, failure);
}
