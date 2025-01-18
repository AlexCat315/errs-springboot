import { invoke } from "@tauri-apps/api/core";

export function get_system_theme(): Promise<boolean> {
  return invoke<string>("get_system_theme") // 指定返回类型为 string
    .then((theme) => theme.toLowerCase() === "dark")
    .catch((error) => {
      console.error("获取系统主题时出错:", error);
      return false; // 出错时返回默认值
    });
}
