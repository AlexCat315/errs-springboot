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

export function get_game_highest_rated(
  start: number,
  success: Function,
  failure: Function,
  error: Function,
) {
  Get(
    `/api/user/game/get/highest-rated?start=${start}`,
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

export function get_game_most_reviewed(
  start: number,
  success: Function,
  failure: Function,
  error: Function,
) {
  Get(
    `/api/user/game/get/most-reviewed?start=${start}`,
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

