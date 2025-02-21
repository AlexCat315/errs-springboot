mod http_client;
mod utils;

use crate::http_client::{get_request, post_request};
use crate::utils::gpu_utils::set_gpu_acceleration;
use crate::utils::img_utils::{conflate_img, get_img_names};
use crate::utils::theme_utils::get_system_theme;
use crate::utils::mac_utils::get_mac_address;
use tauri::Manager;
use tauri_plugin_autostart::MacosLauncher;


#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .plugin(tauri_plugin_shell::init())
        .plugin(tauri_plugin_autostart::init(
            MacosLauncher::LaunchAgent,
            Some(vec!["--flag1", "--flag2"]),
        ))
        .plugin(tauri_plugin_store::Builder::new().build())
        .setup(|app| {
            let window = app.get_window("main").unwrap();

            // macOS 模糊特效
            #[cfg(target_os = "macos")]
            {
                use window_vibrancy::{apply_vibrancy, NSVisualEffectMaterial};
                apply_vibrancy(&window, NSVisualEffectMaterial::HudWindow, None, None)
                    .expect("macOS vibrancy failed");
            }

            // Windows 模糊特效
            #[cfg(target_os = "windows")]
            {
                use window_vibrancy::apply_blur;
                apply_blur(&window, Some((18, 18, 18, 125)))
                    .expect("Windows blur failed");
            }

            Ok(())
        })
        .plugin(tauri_plugin_http::init())
        .plugin(tauri_plugin_opener::init())
        .invoke_handler(tauri::generate_handler![
            get_request,
            post_request,
            conflate_img,
            get_img_names,
            get_system_theme,
            set_gpu_acceleration,
            get_mac_address
        ])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}