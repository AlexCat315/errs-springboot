import { defaultFailure, post } from "../post.js";

export function get_game_info_by_id(
  game_id: number,
  success: Function,
  failure = defaultFailure,
) {
  post(`/api/admin/game/get/info?gameId=${game_id}`, {}, success, failure);
}

export function get_game_info_by_seach(
  searchText: string,
  page:number,
  size:number,
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/game/get/info/search',
    {
      searchText: searchText,
      page: page,
      size: size,
    },
    success,
    failure,
  );
}
