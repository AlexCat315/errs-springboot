import { inject } from "vue";

export function setSelectMeunKey(key: number) {
  // 接收全局变量
  const globalSelect = inject("globalSelect");
  // 检查 globalSelect 是否存在并设置默认值
  if (!globalSelect) {
    throw new Error("globalSelect is not provided");
  }
  // 设置全局变量，将key设置为全局变量的值
  inject("globalSelect", key);
}

export function getSelectMeunKey() {
  // 接收全局变量
  const globalSelect = inject("globalSelect");
  // 检查 globalSelect 是否存在并设置默认值
  if (!globalSelect) {
    throw new Error("globalSelect is not provided");
  }
  // 返回全局变量的值
  return globalSelect;
}
