use image::{imageops::FilterType, ImageBuffer, Rgba};
use std::fs::File;
use std::io::{BufReader, BufWriter};
use std::path::PathBuf;
use tauri::command;

// 定义一个命令，用于合并图片
#[command]
pub async fn conflate_img(vec: Vec<String>, output_dir: String,name : String) -> Result<String, String> {
    if vec.is_empty() {
        return Err("No images provided".to_string());
    }

    let mut imgs = Vec::new();
    let mut total_width = 0;
    let mut target_height = None;

    // 首先加载所有图片并确定目标高度
    for path in &vec {
        let file = File::open(path).map_err(|e| e.to_string())?;
        let reader = BufReader::new(file);
        let format = image::ImageFormat::from_path(path).map_err(|e| e.to_string())?;
        let img = image::load(reader, format).map_err(|e| e.to_string())?;

        if let Some(h) = target_height {
            if img.height() != h {
                // 如果图片高度不一致，调整为目标高度
                let resized_img = img.resize_to_fill(img.width(), h, FilterType::Lanczos3);
                let img_width = resized_img.width(); // 先获取宽度
                imgs.push((resized_img, img_width)); // 再移动 resized_img
                total_width += img_width;
            } else {
                let img_width = img.width(); // 先获取宽度
                imgs.push((img, img_width)); // 再移动 img
                total_width += img_width;
            }
        } else {
            // 设置第一张图片的高度为目标高度
            target_height = Some(img.height());
            let img_width = img.width(); // 先获取宽度
            imgs.push((img, img_width)); // 再移动 img
            total_width += img_width;
        }
    }

    let target_height = target_height.ok_or("No images provided".to_string())?;

    // 创建输出目录（如果不存在）
    std::fs::create_dir_all(&output_dir).map_err(|e| e.to_string())?;

    // 生成输出文件路径
    let output_path = PathBuf::from(output_dir).join(name + ".png");

    // 创建新的图像
    let mut new_img: ImageBuffer<Rgba<u8>, Vec<u8>> = ImageBuffer::new(total_width, target_height);

    let mut x = 0;
    for (img, img_width) in imgs {
        let img = img.to_rgba8();
        for y in 0..target_height {
            for w in 0..img_width {
                let pixel = img.get_pixel(w, y);
                new_img.put_pixel(x + w, y, *pixel);
            }
        }
        x += img_width;
    }

    // 将图片保存为 PNG 格式（如果文件已存在，直接覆盖）
    // 将图片保存为 PNG 格式并压缩
    compress_png(&new_img, &output_path).map_err(|e| e.to_string())?;

    // 返回文件路径
    Ok(output_path.to_string_lossy().to_string())
}

// 定义一个命令，通过路径获取到该目录下的所有图片的名字如{"/path/to/img1.jpg", "/path/to/img2.png"}
#[command]
pub async fn get_img_names(dir: String) -> Result<Vec<String>, String> {
    let mut img_paths = Vec::new();
    let dir_path = PathBuf::from(dir);

    if dir_path.is_dir() {
        for entry in std::fs::read_dir(&dir_path).map_err(|e| e.to_string())? {
            let entry = entry.map_err(|e| e.to_string())?;
            let file_path = entry.path();

            if file_path.is_file() {
                if let Some(extension) = file_path.extension() {
                    if extension == "jpg" || extension == "png" {
                        img_paths.push(file_path.to_string_lossy().to_string());
                    }
                }
            }
        }
    } else {
        return Err("Path is not a directory".to_string());
    }

    Ok(img_paths)
}
use png::{Compression, Encoder};


// 压缩 PNG 图像
fn compress_png(image: &ImageBuffer<Rgba<u8>, Vec<u8>>, output_path: &PathBuf) -> Result<(), String> {
    let file = File::create(output_path).map_err(|e| e.to_string())?;
    let writer = BufWriter::new(file);

    let (width, height) = image.dimensions();
    let mut encoder = Encoder::new(writer, width, height);
    encoder.set_compression(Compression::Best); // 设置最高压缩级别
    let mut writer = encoder.write_header().map_err(|e| e.to_string())?;

    // 将图像数据写入文件
    writer.write_image_data(&image).map_err(|e| e.to_string())?;

    Ok(())
}