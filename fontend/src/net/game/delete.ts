import { defaultFailure, post } from "../post.js";


export async function delete_game_by_id(
  game_id: number,
  success: Function,
  failure = defaultFailure,
) {
  post(`/api/admin/game/delete/by/id?gameId=${game_id}`, {}, success, failure);
}
