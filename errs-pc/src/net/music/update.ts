import { Post } from "../post";


export function update_music_likes(
  id: number,
  isLiked: boolean,
  success: Function,
  failure: Function,
) {
  Post(
    `/api/user/song/update/score-users`,
    {
      id: id,
      isLiked: isLiked
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}
