import { Get } from "../get";
import { Post } from "../post";

export function validate_email(
  email: string,
  success: Function,
  failure: Function,
  error: Function
) {
  Get(
    `/api/user/account/validate/email?email=${email}`,
    (data: any) => {
      success(data);
      console.log(data);
    },
    (message: string) => {
      failure(message);
    },
    (message: string) => {
      error(message);
    }
  );
}

export function validate_code(
  email: string,
  code: string,
  success: Function,
  failure: Function
) {
  Post(
    "/api/user/account/validate/email-code",
    {
      email: email,
      code: code,
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    }
  );
}

export function register(
  RegisterForm: any,
  success: Function,
  failure: Function
) {
  Post(
    "/api/user/account/register",
    {
      password: RegisterForm.password,
      repeatPassword: RegisterForm.password_confirmation,
      email: RegisterForm.email,
      code: RegisterForm.code,
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    }
  );
}
