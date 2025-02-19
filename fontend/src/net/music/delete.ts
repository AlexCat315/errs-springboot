import { defaultFailure, post } from "../post.js";


export async function delete_music_by_id(
  music_id: number,
  success: Function,
  failure = defaultFailure,
) {
  post(`/api/admin/song/delete/by/id?musicId=${music_id}`, {}, success, failure);
}
