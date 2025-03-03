import { defaultFailure, post } from "../post.js";

export function getALl(
  success: Function,
  failure = defaultFailure,
) {
  post(
    '/api/admin/count/all',
    {
    },
    success,
    failure,
  );
}

export function getlineDataAll(
  startTime: Date,
  endTime: Date,
  success: Function,
  failure = defaultFailure,
) {
  post(
    `/api/admin/count/all/time`,
    {
      startTime: startTime,
      endTime: endTime,
    },
    success,
    failure,
  );
}

export function getReviewLists(
  success: Function,
  failure = defaultFailure,
) {
  post(
    `/api/admin/user/get/user/review/list`,
    {

    },
    success,
    failure,
  )
}

export function getReviewHistoryLists(
  success: Function,
  failure = defaultFailure,
) {
  post(
    `/api/admin/user/get/user/review/history/list`,
    {

    },
    success,
    failure,
  )
}

export function updateUserReview(
  userId:number,
  status:number,
  result:string,
  success: Function,
  failure = defaultFailure,
) {
  post(
    `/api/admin/user/update/user/review`,
    {
      userId: userId,
      status: status,
      result: result,
    },
    success,
    failure,
  )
}