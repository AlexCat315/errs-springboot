import { Post } from "../post";

export function askAi(
  question: string,
  success: Function,
  failure: Function,
) {
  Post(
    `/api/user/ai/ask/deepseek?question=${question}`,
    {},
    (data: any) => {
      success(data);
    },
    (message: string) => {
      failure(message);
    },
  );
}
