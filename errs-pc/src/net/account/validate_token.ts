import { Get } from "../get";

export function validate_token(
  token: string,
  success: Function,
  failure: Function,
  error: Function
) {
  Get(
    `/api/user/account/validate/token?token=${token}`,
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },(message: string)=>{
      error(message);
    }
  );
}
