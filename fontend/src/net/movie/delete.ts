import { defaultFailure, post } from "../post.js";


export async function delete_movie_by_id(
  movie_id: number,
  success: Function,
  failure = defaultFailure,
) {
  post(`/api/admin/movie/delete/by/id?id=${movie_id}`, {}, success, failure);
}
