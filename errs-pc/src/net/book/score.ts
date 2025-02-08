import { Get } from "../get";
import { Post } from "../post";

export function score(
  bId: number,
  score: number,
  success: Function,
  failure: Function,
) {
  console.log("bId: ", bId);
  console.log("score: ", score);
  Post(
    "/api/user/book/score",
    {
      score: score,
      bId: bId,
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}

export function validate_score(
  bId: number,
  success: Function,
  failure: Function,
  error: Function,
) {
  console.log("bId: ", bId);
  Get(
    `/api/user/book/validate/score?bId=${bId}`,
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
    (message: string) => {
      error(message);
    },
  );
}
