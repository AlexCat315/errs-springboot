use std::env;

fn enable_gpu_acceleration() {
    env::set_var("WEBKIT_DISABLE_COMPOSITING_MODE", "0"); // 启用 GPU 加速
}

fn disable_gpu_acceleration() {
    env::set_var("WEBKIT_DISABLE_COMPOSITING_MODE", "1"); // 禁用 GPU 加速
}

#[tauri::command]
pub fn set_gpu_acceleration(enabled: bool) {
    if enabled {
        enable_gpu_acceleration();
    } else {
        disable_gpu_acceleration();
    }
}
