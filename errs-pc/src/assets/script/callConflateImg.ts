import {invoke} from "@tauri-apps/api/core";

export async function callConflateImg() {
    try {
        //利用ts调用 获取 ../src/assets/background/下的所有图片文件路径
        const imagePaths = await invoke<string[]>('get_img_names', {
            dir: '../src/assets/background',
            exclude: ['background.png']
        });

        // 查询 local storage 中imagePaths 的值是否相同
        const localStorageImagePaths = localStorage.getItem('imagePaths');

        if (localStorageImagePaths !== null){
            if (JSON.stringify(imagePaths) === localStorageImagePaths) {
                return;
            }
        }

        // 指定输出目录
        const outputDir = '../src/assets/background';

        // 将图片文件路径和输出目录传递给 Tauri 命令
        const result = await invoke<string>('conflate_img', {
            vec: imagePaths,
            outputDir: outputDir,
            name: 'background'
        });
        // 存储最新图片路径到 local storage
        localStorage.setItem('imagePaths', JSON.stringify(imagePaths));

        console.log('调用命令成功:', result);
    } catch (error) {
        console.error('调用命令失败:', error);
    }
}
