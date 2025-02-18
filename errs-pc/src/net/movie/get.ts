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

export function get_move_search(
  page: number,
  size: number,
  searchText: string,
  success: Function,
  failure: Function,
) {
  Post(
    `/api/user/movie/get/search`,
    {
      page: page,
      size: size,
      searchText: searchText
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}