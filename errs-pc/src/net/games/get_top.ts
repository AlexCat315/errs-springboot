import { Get } from "../get";

export function get_game_top50_info(
  start: number,
  success: Function,
  failure: Function,
  error: Function,
) {
  Get(
    `/api/user/game/get/top50?start=${start}`,
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
