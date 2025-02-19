
import { defaultFailure, post } from "../post.js";


export function get_music_info_by_search(
  searchText: string,
  page: number,
  size: number,
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/song/get/search',
    {
      searchText: searchText,
      page: page,
      size: size,
    },
    success,
    failure,
  );
}


export function get_music_info_all(
  page: number,
  size: number,
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/song/get/info/all',
    {
      page: page,
      size: size,
    },
    success,
    failure,
  );
}