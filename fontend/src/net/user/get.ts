import { defaultFailure, post } from "../post.js";

export async function get_user_info_all(
  page: number,
  size: number,
  success: Function,
  failure = defaultFailure,
) {
  post(`/api/admin/user/get/info/all`, {
    page: page,
    size: size
  }, success, failure);
}


export async function get_user_info_search(
  page: number,
  size: number,
  searchText: string,
  success: Function,
  failure = defaultFailure,
) {
  post(`/api/admin/user/search/info`, {
    page: page,
    size: size,
    searchText: searchText
  }, success, failure);
}
