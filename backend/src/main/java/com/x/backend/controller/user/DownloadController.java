package com.x.backend.controller.user;


import com.x.backend.util.MinioUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor // Lombok注解，自动注入构造函数
public class DownloadController {

    private final MinioUtils minioUtils;

    @GetMapping("/{osType}")
    public void downloadFile(@PathVariable String osType, HttpServletResponse response) {
        try {
            // 确定文件名和存储类型
            String filename;

            filename = switch (osType.toLowerCase()) {
                case "windows" -> "RevoCat-Windows-v0.1.1.exe";
                case "macos" -> "RevoCat-macOS-v0.1.1.dmg";
                case "linux" -> "RevoCat-Linux-v0.1.1.AppImage";
                default -> throw new RuntimeException("Unsupported OS type");
            };

            // 获取文件流
            InputStream fileStream = minioUtils.pubDownloadFile(filename);

            // 设置响应头
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

            // 流式传输
            try (OutputStream os = response.getOutputStream()) {
                IOUtils.copy(fileStream, os);
                os.flush();
            } finally {
                if (fileStream != null) {
                    try {
                        fileStream.close();
                    } catch (IOException e) {
                        log.error("关闭文件流失败", e);
                    }
                }
            }

        } catch (Exception e) {
            log.error("文件下载失败: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("文件下载失败: " + e.getMessage());
            } catch (IOException ex) {
                log.error("写入错误信息失败", ex);
            }
        }
    }
}
