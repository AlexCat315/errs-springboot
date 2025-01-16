import { Post } from "../post";

export async function login(
  LoginForm: any,
  success: Function,
  failure: Function
) {
  console.log("login", LoginForm.username, LoginForm.password);
  Post(
    "/api/user/account/login",
    {
      username: LoginForm.username,
      password: LoginForm.password,
    },
    () => {
      success();
    },
    (message: string) => {
      failure(message);
    }
  );
}
