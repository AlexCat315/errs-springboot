import { defaultFailure, post } from "../post.js";



export function get_book_info_by_search(
  searchText: string,
  page: number,
  size: number,
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/book/get/info/search',
    {
      searchText: searchText,
      page: page,
      size: size,
    },
    success,
    failure,
  );
}

export function get_book_info_all(
  page: number,
  size: number,
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/book/get/info/all',
    {
      page: page,
      size: size,
    },
    success,
    failure,
  );
}
