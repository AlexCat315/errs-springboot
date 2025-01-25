import { useToast } from 'vue-toastification';

// 获取 Toast 实例
const toast = useToast();

// 创建一个函数来显示 toast
export const showToast = (message: string,type:string) => {
  switch (type) {
    case 'success':
      toast.success(message);
      break;
    case 'error':
      toast.error(message);
    case 'warning':
      toast.warning(message);
      break;
    default:
      toast.info(message);
  }
};
