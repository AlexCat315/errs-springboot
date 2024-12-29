import { defaultFailure, post } from "../post.js";

export function register(
  username,
  password,
  repeatPassword,
  email,
  code,
  inviteCode,
  success,
  failure = defaultFailure
) {
  post(
    "/api/admin/account/register",
    {
      username: username,
      password: password,
      repeatPassword: repeatPassword,
      email: email,
      code: code,
      inviteCode: inviteCode,
    },
    success,
    failure
  );
}

export function getEmailCode(email, success, failure = defaultFailure) {
  post(
    `api/admin/account/validate-email-register?email=${email}`,
    {},
    success,
    failure
  );
}
