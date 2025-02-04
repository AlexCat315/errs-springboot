import { Get } from "../get";

export function get_book_top250_info(
  start: number,
  success: Function,
  failure: Function,
  error: Function
) {
  Get(
    `/api/user/book/top-250?start=${start}`,
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