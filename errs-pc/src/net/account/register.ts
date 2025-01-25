import { Get } from "../get";

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
    },
    (message: string) => {
      failure(message);
    },(message: string)=>{
      error(message);
    }
  );
}
