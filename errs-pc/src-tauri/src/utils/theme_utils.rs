use dark_light::{detect, Mode};
use tauri::command;

#[command]
pub async fn get_system_theme() -> Result<String, String> {
    match detect() {
        Ok(theme) => match theme {
            Mode::Dark => Ok("dark".to_string()),
            Mode::Light => Ok("light".to_string()),
            Mode::Unspecified => Ok("Unspecified".to_string()),
        },
        Err(e) => Err(e.to_string()),
    }
}
