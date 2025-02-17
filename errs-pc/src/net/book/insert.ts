import { Post } from "../post";

export function insert_book_comment(
  bookId: number,
  comment: string,
  success: Function,
  failure: Function,
) {
  Post(
    `/api/user/book/insert/comment`,
    {
      bookId: bookId,
      comment: comment,
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}