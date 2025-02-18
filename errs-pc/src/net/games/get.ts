import { Post } from "../post";

export function get_state_favorites_game(
  gId: number,
  success: Function,
  failure: Function,
) {
  Post(
    `/api/user/game/get/state/favorites_game?game_id=${gId}`,
    {},
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}

export function get_games_search(
  page: number,
  size: number,
  searchText: string,
  success: Function,
  failure: Function,
) {
  Post(
    `/api/user/game/get/search`,
    {
      page: page,
      size: size,
      searchText: searchText,
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}

