import { inject } from "vue";

export function setSelectMeunKey(key: number) {
  // 接收全局变量
  const globalSelect = inject("globalSelect");
  // 设置全局变量，将key设置为全局变量的值
  inject("globalSelect", key);
}

export function getSelectMeunKey() {
  // 接收全局变量
  const globalSelect = inject("globalSelect");
  // 返回全局变量的值
  return globalSelect;
}
