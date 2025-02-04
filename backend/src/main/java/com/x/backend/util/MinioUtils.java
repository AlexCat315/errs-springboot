package com.x.backend.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.x.backend.pojo.common.CustomMultipartFile;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.io.ByteArrayOutputStream;


@Slf4j
@Component
public class MinioUtils {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;
    @Value("${minio.pub-bucket}")
    private String pubBucketName;

    private MinioUtils(MinioClient minioClient) {
        this.minioClient = minioClient;
    }


    // 上传文件
    public String uploadFile(MultipartFile file) throws Exception {
        return uploadFile(file, "private");
    }

    // 上传文件-公共桶
    public String pubUploadFile(MultipartFile file) throws Exception {
        return uploadFile(file, "public");
    }

    public String uploadCoverToMinio(String imageUrl) throws Exception {
        // 1. 下载图片
        ByteArrayOutputStream buffer;
        try (InputStream imageStream = downloadImage(imageUrl)) {
            // 2. 将 InputStream 转换为 byte[]
            buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int nRead;
            while ((nRead = imageStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
        }
        buffer.flush();
        byte[] imageBytes = buffer.toByteArray();

        // 3. 创建 MultipartFile
        // 截取url最后的地址名作为文件名
        // 1. 提取文件名
        String fileNameFromUrl = extractFileNameFromUrl(imageUrl);

        // 3. 组合文件名
        String fileName = fileNameFromUrl.replaceFirst("\\.[a-zA-Z0-9]+$", "") + ".png";

        MultipartFile multipartFile = new CustomMultipartFile(imageBytes, fileName);

        // 4. 上传到 Minio
        String uploadedFileName = pubUploadFile(multipartFile); // 上传到公共桶
        log.info("上传成功，文件名：{}", uploadedFileName);
        return uploadedFileName;
    }

    private String extractFileNameFromUrl(String imageUrl) {
        // 使用正则表达式提取文件名
        String pattern = "/([^/]+\\.[a-zA-Z0-9]+)$";
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(imageUrl);

        if (matcher.find()) {
            return matcher.group(1); // 返回匹配的文件名部分
        } else {
            throw new IllegalArgumentException("无法从 URL 中提取文件名: " + imageUrl);
        }
    }

    private InputStream downloadImage(String imageUrl) throws IOException {
        Connection.Response response = Jsoup.connect(imageUrl)
                .ignoreContentType(true) // 忽略内容类型检查
                .timeout(10000) // 设置超时
                .execute();
        return response.bodyStream(); // 返回图片的输入流
    }

    private String uploadFile(MultipartFile file, String accessibility) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String id = snowflake.nextId() + "";
        String fileName = id + "-" + file.getOriginalFilename();
        if (accessibility.equals("public")) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(pubBucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

        } else if (accessibility.equals("private")) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } else {
            throw new RuntimeException("该访问性不合法");
        }
        return fileName;
    }


    // 下载文件
    public InputStream downloadFile(String fileName) throws Exception {
        return downloadFile(fileName, "private");
    }

    public InputStream pubDownloadFile(String fileName) throws Exception {
        return downloadFile(fileName, "public");
    }

    private InputStream downloadFile(String fileName, String accessibility) throws Exception {
        if (accessibility.equals("public")) {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(pubBucketName)
                            .object(fileName)
                            .build()
            );
        } else if (accessibility.equals("private")) {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } else {
            throw new RuntimeException("该访问性不合法");
        }
    }

    // 删除文件
    public void deleteFile(String fileName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
    }

    public void pubDeleteFile(String fileName) throws Exception {

    }

    private void deleteFile(String fileName, String accessibility) throws Exception {
        if (accessibility.equals("public")) {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(pubBucketName)
                            .object(fileName)
                            .build()
            );
        } else if (accessibility.equals("private")) {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(pubBucketName)
                            .object(fileName)
                            .build()
            );
        } else {
            throw new RuntimeException("该访问性不合法");
        }
    }

}
