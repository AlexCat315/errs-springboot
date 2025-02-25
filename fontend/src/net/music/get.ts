import { defaultFailure, post } from "../post.js";


export function get_music_info_by_search(
  searchText: string,
  page: number,
  size: number,
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/song/get/search',
    {
      searchText: searchText,
      page: page,
      size: size,
    },
    success,
    failure,
  );
}


export function get_music_info_all(
  page: number,
  size: number,
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/song/get/info/all',
    {
      page: page,
      size: size,
    },
    success,
    failure,
  );
}



export function get_music_info_by_id(
  musicId: number,
  success: Function,
  failure = defaultFailure,
) {
  post(
    `/api/admin/song/get/info/by/id?musicId=${musicId}`,
    {
    },
    success,
    failure,
  );
}



export function getInsertCount(
  startTime: Date,
  endTime: Date,
  success: Function,
  failure = defaultFailure,
) {
  post(
    `/api/admin/count/song/insert/number`,
    {
      startTime: startTime,
      endTime: endTime,
    },
    success,
    failure,
  );
}


export function getUpdateCount(
  startTime: Date,
  endTime: Date,
  success: Function,
  failure = defaultFailure,
) {
  post(
    `/api/admin/count/song/update/number`,
    {
      startTime: startTime,
      endTime: endTime,
    },
    success,
    failure,
  );
}

export function getDeleteCount(
  startTime: Date,
  endTime: Date,
  success: Function,
  failure = defaultFailure,
) {
  post(
    `/api/admin/count/song/delete/number`,
    {
      startTime: startTime,
      endTime: endTime,
    },
    success,
    failure,
  );
}


export function getSongTypeCount(
  success: Function,
  failure = defaultFailure,
) {
  post(
    `/api/admin/count/song/type/all`,
    {
    },
    success,
    failure,
  );
}