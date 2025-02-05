import { Get } from "../get";

export function get_book_top250_info(
  start: number,
  success: Function,
  failure: Function,
  error: Function
) {
  Get(
    `/api/user/book/top-250?start=${start}`,
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
    (message: string) => {
      error(message);
    }
  );
}

export function get_book_top50_info(
  start: number,
  success: Function,
  failure: Function,
  error: Function
) {
  Get(
    `/api/user/book/top-50?start=${start}`,
    (data: any) => {
      success(data);
      console.log(data);
    },
    (message: string) => {
      failure(message);
    },
    (message: string) => {
      error(message);
    }
  );
}

export function get_book_welcome_info(
  start: number,
  success: Function,
  failure: Function,
  error: Function
) {
  Get(
    `/api/user/book/top-welcome?start=${start}`,
    (data: any) => {
      success(data);
      console.log(data);
    },
    (message: string) => {
      failure(message);
    },
    (message: string) => {
      error(message);
    }
  );
}


export function get_book_hot_info(
  start: number,
  success: Function,
  failure: Function,
  error: Function
) {
  Get(
    `/api/user/book/top-hot?start=${start}`,
    (data: any) => {
      success(data);
      console.log(data);
    },
    (message: string) => {
      failure(message);
    },
    (message: string) => {
      error(message);
    }
  );
}
