import { Post } from "../post";



export function delete_favorites_game(
  gId: number,
  success: Function,
  failure: Function,
){
  Post(
    `/api/user/game/delete/favorites_game?game_id=${gId}`,
    {
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}
