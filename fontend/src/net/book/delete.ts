import { defaultFailure, post } from "../post.js";



export function delete_book_by_id(
  id: number,
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/book/delete/info?id=' + id,
    {

    },
    success,
    failure,
  );
}
