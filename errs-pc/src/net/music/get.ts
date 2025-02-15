import { Post } from "../post";

export function get_music_all(
  page: number,
  size: number,
  success: Function,
  failure: Function,
) {
  Post(
    `/api/user/song/get/all`,
    {
      page: page,
      size: size
    },
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}

export function get_music_by_id(
  id: number,
  success: Function,
  failure: Function,
) {
  Post(
    `/api/user/song/get/by/id?id=${id}`,
    {},
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}
