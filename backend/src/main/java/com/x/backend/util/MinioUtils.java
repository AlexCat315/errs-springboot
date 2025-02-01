package com.x.backend.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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

    private String uploadFile(MultipartFile file, String accessibility) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String id = snowflake.nextId() + "";
        String fileName = file.getOriginalFilename() + "-" + id;
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
