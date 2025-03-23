import { invoke } from "@tauri-apps/api/core";

const baseDevUrl = "http://localhost:12345";
const baseProdUrl = "https://www.alexcat.it.com";
const baseUrl = baseDevUrl;

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

export async function Post(url, body, success, failure) {
  const headers = accessHeader();
  try {
    const response = await invoke("post_request", {
      url: baseUrl + url,
      headers: headers,
      body: body,
    });
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
    if (response.data.code === 200) {
      success(response.data);
    } else {
      failure(response.data.message);
    }
  } catch (error) {
    console.error("Error in Post:", error);
  }
}
