import { Post } from "../post";

export async function logout_user(
  success: Function,
  failure: Function
) {
  Post(
    "/api/user/account/logout",
    {
    },
    (data:any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    }
  );
}
