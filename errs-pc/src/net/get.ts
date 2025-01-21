import { invoke } from "@tauri-apps/api/core";

const baseUrl = "http://127.0.0.1:12345";

function accessHeader() {
  if (
    typeof localStorage.getItem("token") === "string" &&
    localStorage.getItem("token") !== "undefined"
  ) {
    return {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    };
  }
  if (sessionStorage.getItem("token") !== null) {
    return {
      "Content-Type": "application/json",
      Authorization: `Bearer ${sessionStorage.getItem("token")}`,
    };
  }
  return {
    "Content-Type": "application/json",
  };
}

// 定义 ResultEntity 类型
interface ResultEntity<T = any> {
  code?: number;
  data?: T;
  message?: string;
}

/**
 * 发送 GET 请求
 * @param url 请求路径
 * @param success 成功回调函数
 * @param failure 失败回调函数
 */
export async function Get(
  url: string,
  success: (data: any) => void,
  failure: (message: string) => void,
  errorfn: (message: string) => void
) {
  const headers = accessHeader();
  try {
    // 使用类型断言明确 response 的类型
    const response = await invoke<ResultEntity>("get_request", {
      url: baseUrl + url,
      headers: headers,
    });

    // 根据状态码处理响应
    switch (response.code) {
      case 401:
        // 可以跳转到登录页面
        // router.push('/login').then(_r => {});
        break;
      case 403:
        // ElMessage.warning('权限不足，无法访问');
        break;
      case 500:
        // ElMessage.error('服务器内部错误');
        break;
      default:
      // ElMessage.warning('发生了错误');
    }

    // 如果请求成功，调用 success 回调函数
    if (response.code === 200 && response.data.code === 200) {
      success(response.data);
    } else {
      failure(response.data.message || "请求失败");
    }
  } catch (error) {
    errorfn(error)
  }
}
