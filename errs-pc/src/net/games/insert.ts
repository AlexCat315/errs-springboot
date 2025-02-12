import { Post } from "../post";

interface RantingComment {
  gameID: number;
  score: number;
  comment: string;
}

export function add_ranting_comment(
  addFrom: RantingComment,
  success: Function,
  failure: Function,
) {
  Post(
    "/api/user/game/add/ranting-comment",
    {
      id: addFrom.gameID,
      score: addFrom.score,
      comment: addFrom.comment,
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}
