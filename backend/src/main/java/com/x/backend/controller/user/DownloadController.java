package com.x.backend.controller.user;


import com.x.backend.annotation.RoleSecurity;
import com.x.backend.constants.RoleConstants;
import com.x.backend.util.MinioUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor // Lombok注解，自动注入构造函数
@RoleSecurity(RoleConstants.ROLE_ANONYMOUS)
public class DownloadController {

    private final MinioUtils minioUtils;

    @GetMapping("/{osType}")
    public void downloadFile(@PathVariable String osType, @RequestParam(value = "filename", required = false) String filenameFromParam, HttpServletResponse response) {
        try {
            // 确定文件名和存储类型
            String filename = getString(osType, filenameFromParam);

            // 获取文件流
            InputStream fileStream = minioUtils.pubDownloadFile(filename);
            long fileSize = minioUtils.getFileSize(filename); // 新增方法获取文件大小
            // 设置响应头
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            response.setContentLengthLong(fileSize); // 关键：添加文件大小
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

    @NotNull
    private static String getString(String osType, String filenameFromParam) {
        String filename;

        filename = switch (osType.toLowerCase()) {
            case "windows" -> "RevoCat-Windows-v0.1.1.exe";
            case "macos" -> "RevoCat-macos-0.1.1.zip";
            case "linux" -> "RevoCat-Linux-v0.1.1.AppImage";
            default -> throw new RuntimeException("Unsupported OS type");
        };

        // 如果从参数中获取了文件名，则使用它（主要用于iframe方法）
        if (filenameFromParam != null && !filenameFromParam.isEmpty()) {
            filename = filenameFromParam;
        }
        return filename;
    }
}
