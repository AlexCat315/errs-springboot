import { Post } from "../post";

export function get_move_all(
  page: number,
  size: number,
  success: Function,
  failure: Function,
) {
  Post(
    `/api/user/movie/get/all`,
    {
      page: page,
      size: size
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}