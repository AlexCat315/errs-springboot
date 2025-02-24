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