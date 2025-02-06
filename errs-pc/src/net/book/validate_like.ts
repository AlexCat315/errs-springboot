import { Get } from "../get";

export function validate_like(
  bId: number,
  success: Function,
  failure: Function,
  error: Function,
) {
  Get(
    `/api/user/book/validate/like?bId=${bId}`,
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
    (message: string) => {
      error(message);
    },
  );
}
