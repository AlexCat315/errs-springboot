import { defaultFailure, post } from "../post.js";

export async function get_movie_info_by_id(
  movie_id: number,
  success: Function,
  failure = defaultFailure,
) {
  post(`/api/admin/movie/get/info/by/id?id=${movie_id}`, {}, success, failure);
}

export function get_movie_info_by_seach(
  page: number,
  size: number,
  searchText: string,
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/movie/get/info/search',
    {
      searchText: searchText,
      page: page,
      size: size,
    },
    success,
    failure,
  );
}

export function get_movie_info_all(
  page: number,
  size: number,
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/movie/get/info/all',
    {
      page: page,
      size: size,
    },
    success,
    failure,
  );
}
