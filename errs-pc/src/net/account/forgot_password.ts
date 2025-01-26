import { Post } from "../post";

export function send_code(email: string, success: Function, failure: Function) {
  Post(
    `/api/user/account/forgot-password/send-code?email=${email}`,
    {},
    (data: any) => {
      success(data);
    },
    (message: string) => {
      console.log(message);
      failure(message);
    },
  );
}

export function forgot_password(
  forgotPassword: any,
  success: Function,
  failure: Function,
) {
  console.log(forgotPassword);
  Post(
    "/api/user/account/forgot-password",
    {
      email: forgotPassword.email,
      code: forgotPassword.code,
      password: forgotPassword.password,
      repeatPassword: forgotPassword.password_confirm,
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}
