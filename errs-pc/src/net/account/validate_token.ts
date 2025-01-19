import { Get } from "../get";

export function validate_token(
  token: string,
  success: Function,
  failure: Function
) {
    console.log("validate_token", token);
  Get(
    `/api/user/account/validate/token?token=${token}`,
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    }
  );
}
