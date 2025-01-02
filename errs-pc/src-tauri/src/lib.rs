mod http_client;

use crate::http_client::{get_request, post_request};
use tauri::Manager;
use window_vibrancy::{apply_vibrancy, NSVisualEffectMaterial};

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
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
            
            // Linux 

            Ok(())
        })
        .plugin(tauri_plugin_http::init())
        .invoke_handler(tauri::generate_handler![
            get_request,
            post_request

        ])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
