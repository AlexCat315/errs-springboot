mod http_client;
mod utils;

use crate::http_client::{get_request, post_request};
use crate::utils::img_utils::{conflate_img, get_img_names};
use crate::utils::theme_utils::get_system_theme;
use tauri::Manager;
use tauri_plugin_autostart::MacosLauncher;
use window_vibrancy::{apply_vibrancy, NSVisualEffectMaterial};
use crate::utils::gpu_utils::set_gpu_acceleration;

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .plugin(tauri_plugin_autostart::init(
            MacosLauncher::LaunchAgent,       // macOS 启动方式
            Some(vec!["--flag1", "--flag2"]), // 可选：传递给应用程序的参数
        ))
        .plugin(tauri_plugin_store::Builder::new().build())
        .setup(|app| {
            let window = app.get_window("main").unwrap();

            // macOS 上启用 Vibrancy 效果
            #[cfg(target_os = "macos")]
            apply_vibrancy(&window, NSVisualEffectMaterial::HudWindow, None, None)
                .expect("Unsupported platform! 'apply_vibrancy' is only supported on macOS");

            // Windows 上启用 Blur 效果
            #[cfg(target_os = "windows")]
            apply_blur(&window, Some((18, 18, 18, 125)))
                .expect("Unsupported platform! 'apply_blur' is only supported on Windows");

            // Linux 上启用 Blur 效果
            #[cfg(target_os = "linux")]
            apply_blur(&window, Some((18, 18, 18, 125)))
                .expect("Unsupported platform! 'apply_blur' is only supported on Linux");

            Ok(())
        })
        .plugin(tauri_plugin_http::init())
        .invoke_handler(tauri::generate_handler![
            get_request,
            post_request,
            conflate_img,
            get_img_names,
            get_system_theme,
            set_gpu_acceleration
        ])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
