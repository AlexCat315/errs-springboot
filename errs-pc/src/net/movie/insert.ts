import { Post } from "../post";

export function insert_movie_rating_comment(
  movieId: number,
  comment: string,
  rating: number,
  success: Function,
  failure: Function,
) {
  Post(
    `/api/user/movie/insert/rating-comment`,
    {
      movieId: movieId,
      comment: comment,
      rating: rating,
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}