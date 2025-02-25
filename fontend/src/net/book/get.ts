import { defaultFailure, post } from "../post.js";



export function get_book_info_by_search(
  searchText: string,
  page: number,
  size: number,
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/book/get/info/search',
    {
      searchText: searchText,
      page: page,
      size: size,
    },
    success,
    failure,
  );
}

export function get_book_info_all(
  page: number,
  size: number,
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/book/get/info/all',
    {
      page: page,
      size: size,
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
    `/api/admin/count/book/insert/number`,
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
    `/api/admin/count/book/update/number`,
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
    `/api/admin/count/book/delete/number`,
    {
      startTime: startTime,
      endTime: endTime,
    },
    success,
    failure,
  );
}


export function getBookTypeCount(
  success: Function,
  failure = defaultFailure,
) {
  post(
    `/api/admin/count/book/type/all`,
    {
    },
    success,
    failure,
  );
}