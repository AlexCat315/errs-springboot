import { Post } from "../post";

export async function login(
  LoginForm: any,
  success: Function,
  failure: Function
) {
  Post(
    "/api/user/account/login",
    {
      username: LoginForm.username,
      password: LoginForm.password,
    },
    (data:any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    }
  );
}
