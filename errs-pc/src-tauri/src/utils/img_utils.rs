use image::{imageops::FilterType, ImageBuffer, Rgba};
use png::Encoder;
use std::ffi::OsStr;
use std::fs::File;
use std::io::BufWriter;
use std::path::PathBuf;
use tauri::command;

#[command]
pub async fn conflate_img(vec: Vec<String>, output_dir: String, name: String) -> Result<String, String> {
    if vec.is_empty() {
        return Err("No images provided".to_string());
    }

    let mut imgs = Vec::new();
    let mut total_width = 0;
    let mut target_height = None;

    // 处理每张图片
    for path in &vec {
        let img = image::open(path).map_err(|e| format!("Failed to open image {}: {}", path, e))?;
        let img = img.to_rgba8(); // 确保图片格式一致

        if let Some(h) = target_height {
            if img.height() != h {
                // 按比例缩放宽度
                let width = (img.width() as f32 * (h as f32 / img.height() as f32)) as u32;
                let resized_img = image::imageops::resize(&img, width, h, FilterType::Lanczos3);
                total_width += resized_img.width();
                imgs.push(resized_img);
            } else {
                total_width += img.width();
                imgs.push(img);
            }
        } else {
            target_height = Some(img.height());
            total_width += img.width();
            imgs.push(img);
        }
    }

    let target_height = target_height.ok_or("No images provided".to_string())?;

    // 创建输出目录
    std::fs::create_dir_all(&output_dir).map_err(|e| format!("Failed to create output directory: {}", e))?;
    let output_path = PathBuf::from(output_dir).join(format!("{}.png", name));

    // 创建新的图片缓冲区
    let mut new_img: ImageBuffer<Rgba<u8>, Vec<u8>> = ImageBuffer::new(total_width, target_height);
    let mut x_offset = 0;

    // 拼接图片
    for img in imgs {
        for (x, y, pixel) in img.enumerate_pixels() {
            new_img.put_pixel(x_offset + x, y, *pixel);
        }
        x_offset += img.width();
    }

    // 保存图片
    let file = File::create(&output_path).map_err(|e| format!("Failed to create output file: {}", e))?;
    let w = BufWriter::new(file);
    let mut encoder = Encoder::new(w, new_img.width(), new_img.height());
    encoder.set_color(png::ColorType::Rgba);
    encoder.set_depth(png::BitDepth::Eight);
    encoder.set_compression(png::Compression::Best); // 使用最高压缩级别

    let mut writer = encoder.write_header().map_err(|e| format!("Failed to write PNG header: {}", e))?;
    writer.write_image_data(&new_img.into_raw()).map_err(|e| format!("Failed to write PNG data: {}", e))?;

    Ok(output_path.to_string_lossy().to_string())
}



#[command]
pub async fn get_img_names(dir: String, exclude: Vec<String>) -> Result<Vec<String>, String> {
    let mut img_paths = Vec::new();
    let dir_path = PathBuf::from(dir);

    if !dir_path.is_dir() {
        return Err("Path is not a directory".to_string());
    }

    // 支持的图片扩展名
    let supported_extensions = ["jpg", "jpeg", "png", "gif", "bmp", "webp"];

    for entry in std::fs::read_dir(&dir_path).map_err(|e| e.to_string())? {
        let entry = entry.map_err(|e| e.to_string())?;
        let file_path = entry.path();

        if file_path.is_file() {
            if let Some(extension) = file_path.extension().and_then(OsStr::to_str) {
                if supported_extensions.contains(&extension.to_lowercase().as_str()) {
                    let file_name = file_path.file_name().and_then(OsStr::to_str).unwrap_or("");
                    if !exclude.contains(&file_name.to_string()) {
                        img_paths.push(file_path.to_string_lossy().to_string());
                    }
                }
            }
        }
    }

    Ok(img_paths)
}