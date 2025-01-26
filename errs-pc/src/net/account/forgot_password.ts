import { Post } from "../post";

export function send_code(email: string, success: Function, failure: Function) {
  Post(
    "/api/user/account/forgot-password/send-code",
    {
      email: email,
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    }
  );
}
